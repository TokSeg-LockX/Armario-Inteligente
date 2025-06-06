const API_BASE_URL = 'http://localhost:8080/api';
let authToken = null;
let currentEntregaId = null;

const responseArea = document.getElementById('apiResponse');
const actionsSection = document.getElementById('actionsSection');

function displayResponse(data, error = false) {
    if (error) {
        console.error('API Error:', data);
        responseArea.textContent = `Erro: ${data.message || JSON.stringify(data, null, 2)}`;
        responseArea.style.color = 'red';
    } else {
        console.log('API Success:', data);
        responseArea.textContent = JSON.stringify(data, null, 2);
        responseArea.style.color = 'green';
    }
}

async function apiCall(endpoint, method = 'GET', body = null, token = null) {
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    };
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        method: method,
        headers: headers
    };

    if (body) {
        config.body = JSON.stringify(body);
    }

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({ message: `HTTP error! status: ${response.status}` }));
            displayResponse(errorData, true);
            return null;
        }
        if (response.status === 204) { // No Content
            return {};
        }
        const data = await response.json();
        displayResponse(data);
        return data;
    } catch (error) {
        displayResponse({ message: error.message }, true);
        return null;
    }
}

async function login() {
    const email = document.getElementById('emailLogin').value;
    const senha = document.getElementById('senhaLogin').value;

    const data = await apiCall('/auth/login', 'POST', { email, senha });
    if (data && data.token) {
        authToken = data.token;
        localStorage.setItem('authToken', authToken); // Salvar token
        document.getElementById('emailLogin').disabled = true;
        document.getElementById('senhaLogin').disabled = true;
        actionsSection.classList.remove('hidden');
        displayResponse({ message: 'Login bem-sucedido! Token salvo.', user: data.usuario });
    } else {
        authToken = null;
        localStorage.removeItem('authToken');
    }
}

// Carregar token ao iniciar (se existir)
function loadToken() {
    const storedToken = localStorage.getItem('authToken');
    if (storedToken) {
        authToken = storedToken;
        // Poderia adicionar uma lógica para validar o token ou obter dados do usuário
        // Por simplicidade, apenas assumimos que está válido e mostramos as ações.
        actionsSection.classList.remove('hidden');
        displayResponse({ message: 'Token carregado do localStorage. Se precisar, faça login novamente para um novo token.' });

    }
}
window.onload = loadToken;


async function solicitarDeposito() {
    if (!authToken) {
        displayResponse({ message: 'Faça login primeiro!' }, true);
        return;
    }
    const compartimentoId = parseInt(document.getElementById('compDepositoId').value);
    const destinatarioId = parseInt(document.getElementById('destinatarioId').value);
    const descricaoPacote = document.getElementById('descricaoPacote').value;

    const result = await apiCall('/entregas/solicitar-deposito', 'POST', {
        compartimentoId,
        destinatarioId,
        descricaoPacote
    }, authToken);

    if (result && result.id) {
        currentEntregaId = result.id;
        document.getElementById('entregaIdConfirmar').value = currentEntregaId;
        displayResponse({
            message: `Solicitação de depósito criada (Entrega ID: ${currentEntregaId}). Verifique o console da sua API para o PIN do Entregador.`,
            data: result
        });
    }
}

async function abrirParaDeposito() {
    if (!authToken) {
        displayResponse({ message: 'Faça login primeiro!' }, true);
        return;
    }
    const compartimentoId = parseInt(document.getElementById('compDepositoId').value); // Usamos o mesmo ID
    const pin = document.getElementById('pinEntregador').value;

    await apiCall('/compartimentos/abrir-com-pin', 'POST', {
        compartimentoId,
        pin,
        tipoUsuarioOperacao: "ENTREGADOR"
    }, authToken);
}

async function confirmarDeposito() {
    if (!authToken) {
        displayResponse({ message: 'Faça login primeiro!' }, true);
        return;
    }
    if (!currentEntregaId) {
        currentEntregaId = parseInt(document.getElementById('entregaIdConfirmar').value);
        if (isNaN(currentEntregaId)) {
            displayResponse({ message: 'ID da Entrega para confirmar não encontrado ou inválido. Solicite um depósito primeiro ou informe o ID.' }, true);
            return;
        }
    }

    const result = await apiCall(`/entregas/${currentEntregaId}/confirmar-deposito`, 'POST', null, authToken);
    if (result) {
        displayResponse({
            message: `Depósito confirmado para Entrega ID: ${currentEntregaId}. Verifique o console da sua API para o PIN do Destinatário.`,
            data: result
        });
    }
}

async function abrirParaRetirada() {
    if (!authToken) {
        displayResponse({ message: 'Faça login (como destinatário) primeiro!' }, true);
        return;
    }
    const compartimentoId = parseInt(document.getElementById('compRetiradaId').value);
    const pin = document.getElementById('pinDestinatario').value;

    await apiCall('/compartimentos/abrir-com-pin', 'POST', {
        compartimentoId,
        pin,
        tipoUsuarioOperacao: "DESTINATARIO"
    }, authToken);
}