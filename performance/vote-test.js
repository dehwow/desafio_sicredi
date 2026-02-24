import http from 'k6/http';
import { check, sleep } from 'k6';
//rodar no windows docker run --rm -i -v ${PWD}/performance:/scripts grafana/k6 run /scripts/vote-test.js
// Configuração do teste
export const options = {
    scenarios: {
        vote_scenario: {
            executor: 'constant-vus',
            vus: 200,
            duration: '30s',
            exec: 'vote',
        },
        read_scenario: {
            executor: 'constant-vus',
            vus: 10,
            duration: '30s',
            exec: 'read_result',
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.05'], // Tolerar até 5% de falhas devido a possíveis erros de negócio (ex: CPF já votou, embora UUID seja randômico)
        http_req_duration: ['p(95)<500'], // 95% das requisições devem ser menores que 500ms
    },
};

const BASE_URL = __ENV.BASE_URL || 'http://host.docker.internal:8080';

// Setup: Criar pauta e abrir sessão para os testes
export function setup() {
    console.log(`Iniciando setup no ambiente: ${BASE_URL}`);

    // 1. Criar uma Pauta
    const pautaRes = http.post(`${BASE_URL}/v1/pautas`, JSON.stringify({
        titulo: `Pauta Performance Test ${Date.now()}`
    }), {
        headers: { 'Content-Type': 'application/json' },
    });

    if (pautaRes.status !== 201) {
        throw new Error(`Falha ao criar pauta: ${pautaRes.status} ${pautaRes.body}`);
    }

    const pautaId = pautaRes.json().id;
    console.log(`Pauta criada com ID: ${pautaId}`);

    // 2. Abrir Sessão (10 minutos para garantir que não expire durante o teste)
    const sessaoRes = http.post(`${BASE_URL}/v1/pautas/${pautaId}/sessao`, JSON.stringify({
        duracaoEmMinutos: 10
    }), {
        headers: { 'Content-Type': 'application/json' },
    });

    if (sessaoRes.status !== 201) {
        throw new Error(`Falha ao abrir sessão: ${sessaoRes.status} ${sessaoRes.body}`);
    }

    console.log(`Sessão aberta para a pauta: ${pautaId}`);

    return { pautaId: pautaId };
}

// Cenário de Escrita: Registrar Votos
export function vote(data) {
    const pautaId = data.pautaId;
    
    // k6 v0.45.0+ suporta crypto.randomUUID() nativamente no escopo global ou via import
    // Caso o k6 seja antigo, pode falhar aqui.
    const associadoId = crypto.randomUUID();
    
    // Gerar um CPF fictício de 11 dígitos
    const cpf = Math.floor(Math.random() * 90000000000 + 10000000000).toString();

    const payload = JSON.stringify({
        pautaId: pautaId,
        associadoId: associadoId,
        cpf: cpf,
        voto: Math.random() > 0.5 ? 'SIM' : 'NAO'
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(`${BASE_URL}/v1/pautas/${pautaId}/votos`, payload, params);

    check(res, {
        'voto registrado (201)': (r) => r.status === 201,
    });
}

// Cenário de Leitura: Consultar Resultado
export function read_result(data) {
    const pautaId = data.pautaId;
    const res = http.get(`${BASE_URL}/v1/pautas/${pautaId}/resultado`);

    check(res, {
        'resultado obtido (200)': (r) => r.status === 200,
    });
    
    sleep(1); 
}
