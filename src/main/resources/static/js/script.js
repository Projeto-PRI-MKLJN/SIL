/**
 * S.I.L - INFO - Sistema para Intérpretes de Libras na Área da Informática
 * Script de Interatividade e UX (Otimizado para Spring Boot MVC e CSP)
 */

const ThemeManager = {
    currentTheme: 'light',

    init() {
        const savedTheme = localStorage.getItem('theme') || 'light';
        this.setTheme(savedTheme);
        this.addToggleButton();
    },

    setTheme(theme) {
        this.currentTheme = theme;
        document.documentElement.setAttribute('data-theme', theme);
        localStorage.setItem('theme', theme);
        this.updateToggleButton();
    },

    toggleTheme() {
        const newTheme = this.currentTheme === 'light' ? 'dark' : 'light';
        this.setTheme(newTheme);
    },

    addToggleButton() {
        const headerContent = document.querySelector('.header-content');
        if (headerContent && !document.getElementById('theme-toggle-btn')) {
            const button = document.createElement('button');
            button.id = 'theme-toggle-btn';
            button.className = 'theme-toggle';
            button.addEventListener('click', () => this.toggleTheme());
            headerContent.appendChild(button);
            this.updateToggleButton();
        }
    },

    updateToggleButton() {
        const button = document.getElementById('theme-toggle-btn');
        if (!button) return;

        if (this.currentTheme === 'light') {
            button.innerHTML = `
                <svg viewBox="0 0 24 24" fill="currentColor"><path d="M17.75,4.09L15.22,6.03L16.13,9.09L13.5,7.28L10.87,9.09L11.78,6.03L9.25,4.09L12.44,4L13.5,1L14.56,4L17.75,4.09M21.25,11L19.61,12.25L20.2,14.23L18.5,13.06L16.8,14.23L17.39,12.25L15.75,11L17.81,10.95L18.5,9L19.19,10.95L21.25,11M18.97,15.95C19.8,15.87 20.69,17.05 20.16,17.8C19.84,18.25 19.5,18.67 19.08,19.07C15.17,23 8.84,23 4.94,19.07C1.03,15.17 1.03,8.83 4.94,4.93C5.34,4.53 5.76,4.17 6.21,3.85C6.96,3.32 8.14,4.21 8.06,5.04C7.79,7.9 8.75,10.87 10.95,13.06C13.14,15.26 16.1,16.22 18.97,15.95M17.33,17.97C14.5,17.81 11.7,16.64 9.53,14.5C7.36,12.31 6.2,9.5 6.04,6.68C3.23,9.82 3.34,14.64 6.35,17.66C9.37,20.67 14.19,20.78 17.33,17.97Z"/></svg>
                <span>Escuro</span>
            `;
        } else {
            button.innerHTML = `
                <svg viewBox="0 0 24 24" fill="currentColor"><path d="M12,7A5,5 0 0,1 17,12A5,5 0 0,1 12,17A5,5 0 0,1 7,12A5,5 0 0,1 12,7M12,9A3,3 0 0,0 9,12A3,3 0 0,0 12,15A3,3 0 0,0 15,12A3,3 0 0,0 12,9M12,2L14.39,5.42C13.65,5.15 12.84,5 12,5C11.16,5 10.35,5.15 9.61,5.42L12,2M3.34,7L7.5,6.65C6.9,7.16 6.36,7.78 5.94,8.5C5.5,9.24 5.25,10 5.11,10.79L3.34,7M3.36,17L5.12,13.23C5.26,14 5.53,14.78 5.95,15.5C6.37,16.24 6.91,16.86 7.5,17.37L3.36,17M20.65,7L18.88,10.79C18.74,10 18.47,9.23 18.05,8.5C17.63,7.78 17.1,7.15 16.5,6.64L20.65,7M20.64,17L16.5,17.36C17.09,16.85 17.62,16.22 18.04,15.5C18.46,14.77 18.73,14 18.87,13.21L20.64,17M12,22L9.59,18.56C10.33,18.83 11.14,19 12,19C12.82,19 13.63,18.83 14.37,18.56L12,22Z"/></svg>
                <span>Claro</span>
            `;
        }
    }
};

function filtrarSinais() {
    const input = document.getElementById('search-input');
    if (!input) return;

    const termo = input.value.toLowerCase().trim();
    const cards = document.querySelectorAll('.sinal-card');

    cards.forEach(card => {
        const text = card.innerText.toLowerCase();
        card.style.display = text.includes(termo) ? 'flex' : 'none';
    });
}

const MenuManager = {
    init() {
        document.addEventListener('click', (e) => {
            const hamburger = document.getElementById('hamburger');
            const nav = document.getElementById('main-nav');
            const overlay = document.getElementById('nav-overlay');
            
            if (!hamburger || !nav || !overlay) return;

            if (hamburger.contains(e.target)) {
                this.toggleMenu(nav, overlay, hamburger);
            } else if (e.target === overlay) {
                this.closeMenu(nav, overlay, hamburger);
            }
        });
    },

    toggleMenu(nav, overlay, hamburger) {
        const isActive = nav.classList.toggle('active');
        overlay.classList.toggle('active');
        hamburger.classList.toggle('active');
        document.body.style.overflow = isActive ? 'hidden' : '';
    },

    closeMenu(nav, overlay, hamburger) {
        nav.classList.remove('active');
        overlay.classList.remove('active');
        hamburger.classList.remove('active');
        document.body.style.overflow = '';
    }
};

const ModalManager = {
    open(id, isEdit = false) {
        const modal = document.getElementById(id);
        if (modal) {
            const form = modal.querySelector('form');
            if (form && !isEdit) {
                form.reset();
                const idField = form.elements['id'];
                if (idField) idField.value = '';
                
                const title = modal.querySelector('h2');
                if (title) {
                    if (id === 'sign-modal') title.innerText = 'Novo Sinal';
                    if (id === 'user-modal') title.innerText = 'Novo Usuário';
                }

                const submitBtn = modal.querySelector('button[type="submit"]');
                if (submitBtn) {
                    if (id === 'user-modal') submitBtn.innerText = 'Criar Conta';
                }

                const passwordInput = form.elements['password'];
                if (passwordInput) {
                    passwordInput.required = true;
                    passwordInput.placeholder = 'Mínimo 6 caracteres';
                }
            }
            modal.classList.add('active');
            document.body.style.overflow = 'hidden';
        }
    },
    close(id) {
        const modal = document.getElementById(id);
        if (modal) {
            modal.classList.remove('active');
            document.body.style.overflow = '';
        }
    }
};

const ConfirmDialogManager = {
    dialog: null,
    confirmBtn: null,
    cancelBtn: null,
    messageElement: null,
    onConfirmCallback: null,

    init() {
        this.dialog = document.getElementById('custom-confirm-dialog');
        if (!this.dialog) return;

        this.confirmBtn = document.getElementById('confirm-dialog-confirm-btn');
        this.cancelBtn = document.getElementById('confirm-dialog-cancel-btn');
        this.messageElement = document.getElementById('confirm-dialog-message');

        this.cancelBtn.addEventListener('click', () => this.close());
        this.confirmBtn.addEventListener('click', () => {
            if (this.onConfirmCallback) this.onConfirmCallback();
            this.close();
        });
    },

    open(message, onConfirm) {
        if (!this.dialog) return;
        this.messageElement.innerText = message;
        this.onConfirmCallback = onConfirm;
        this.dialog.showModal();
        document.body.style.overflow = 'hidden';
    },

    close() {
        if (!this.dialog) return;
        this.dialog.close();
        document.body.style.overflow = '';
    }
};

const ToastManager = {
    container: null,

    init() {
        this.container = document.getElementById('toast-container');
        if (!this.container) return;

        const successMsg = this.container.dataset.toastSuccess;
        const errorMsg = this.container.dataset.toastError;

        if (successMsg && successMsg !== 'null' && successMsg !== '') {
            this.show(successMsg, 'success');
        }
        if (errorMsg && errorMsg !== 'null' && errorMsg !== '') {
            this.show(errorMsg, 'error');
        }
    },

    show(message, type = 'success') {
        if (!this.container) return;

        const toast = document.createElement('div');
        toast.className = `toast toast-${type} animate-slide-in`;

        const title = type === 'success' ? 'Sucesso' : 'Erro';
        const iconSvg = type === 'success' ? `
            <svg class="toast-svg-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
                <polyline points="20 6 9 17 4 12"></polyline>
            </svg>
        ` : `
            <svg class="toast-svg-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
                <line x1="18" y1="6" x2="6" y2="18"></line>
                <line x1="6" y1="6" x2="18" y2="18"></line>
            </svg>
        `;

        toast.innerHTML = `
            <div class="toast-icon-wrapper">${iconSvg}</div>
            <div class="toast-content-wrapper">
                <div class="toast-title">${title}</div>
                <div class="toast-message">${message}</div>
            </div>
            <button class="toast-close" aria-label="Fechar">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                    <line x1="18" y1="6" x2="6" y2="18"></line>
                    <line x1="6" y1="6" x2="18" y2="18"></line>
                </svg>
            </button>
        `;

        this.container.appendChild(toast);

        const autoRemove = setTimeout(() => {
            this.dismiss(toast);
        }, 5000);

        toast.querySelector('.toast-close').addEventListener('click', () => {
            clearTimeout(autoRemove);
            this.dismiss(toast);
        });
    },

    dismiss(toast) {
        toast.classList.add('animate-slide-out');
        toast.addEventListener('animationend', () => {
            toast.remove();
        });
    }
};



window.openEditSignModal = (data) => {
    const form = document.getElementById('sign-form');
    if (!form) return;

    document.getElementById('modal-title').innerText = 'Editar Sinal';
    if (form.elements['id']) form.elements['id'].value = data.id || '';
    if (form.elements['term']) form.elements['term'].value = data.term || '';
    if (form.elements['category']) form.elements['category'].value = data.category || '';
    if (form.elements['description']) form.elements['description'].value = data.description || '';
    if (form.elements['videoUrl']) form.elements['videoUrl'].value = data.videoUrl || '';
    
    ModalManager.open('sign-modal', true);
};

window.openEditUserModal = (data) => {
    const form = document.getElementById('user-form');
    if (!form) return;

    document.getElementById('user-modal-title').innerText = 'Editar Usuário';
    if (form.elements['id']) form.elements['id'].value = data.id || '';
    if (form.elements['name']) form.elements['name'].value = data.name || '';
    if (form.elements['email']) form.elements['email'].value = data.email || '';
    if (form.elements['role']) form.elements['role'].value = data.role || '';

    const passwordInput = form.elements['password'];
    if (passwordInput) {
        passwordInput.required = false;
        passwordInput.placeholder = 'Deixe em branco para manter a atual';
    }

    const label = document.getElementById('user-password-label');
    if (label) label.innerText = 'Nova Senha (opcional)';

    const submitBtn = document.getElementById('user-submit-btn');
    if (submitBtn) submitBtn.innerText = 'Salvar Alterações';

    ModalManager.open('user-modal', true);
};

document.addEventListener('DOMContentLoaded', () => {
    ThemeManager.init();
    MenuManager.init();
    ConfirmDialogManager.init();
    ToastManager.init();

    // Gerenciador de eventos delegado para evitar inline JS (CSP)
    document.body.addEventListener('click', (e) => {
        const target = e.target;

        const togglePasswordBtn = target.closest('.toggle-password-btn');
        if (togglePasswordBtn) {
            e.preventDefault();
            const input = togglePasswordBtn.parentNode.querySelector('input');
            if (input) {
                const isPassword = input.type === 'password';
                input.type = isPassword ? 'text' : 'password';
                if (isPassword) {
                    togglePasswordBtn.innerHTML = `
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path>
                            <line x1="1" y1="1" x2="23" y2="23"></line>
                        </svg>
                    `;
                } else {
                    togglePasswordBtn.innerHTML = `
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                            <circle cx="12" cy="12" r="3"></circle>
                        </svg>
                    `;
                }
            }
            return;
        }

        const openBtn = target.closest('[data-modal-open]');
        if (openBtn) ModalManager.open(openBtn.dataset.modalOpen);

        const closeBtn = target.closest('[data-modal-close]');
        if (closeBtn) ModalManager.close(closeBtn.dataset.modalClose);

        const editSignBtn = target.closest('.btn-edit-sign');
        if (editSignBtn) {
            window.openEditSignModal({
                id: editSignBtn.dataset.id,
                term: editSignBtn.dataset.term,
                category: editSignBtn.dataset.category,
                description: editSignBtn.dataset.description,
                videoUrl: editSignBtn.dataset.videoUrl
            });
        }

        const editUserBtn = target.closest('.btn-edit-user');
        if (editUserBtn) {
            window.openEditUserModal({
                id: editUserBtn.dataset.id,
                name: editUserBtn.dataset.name,
                email: editUserBtn.dataset.email,
                role: editUserBtn.dataset.role
            });
        }

        const confirmBtn = target.closest('[data-confirm]');
        if (confirmBtn) {
            e.preventDefault();
            const message = confirmBtn.dataset.confirm;
            const targetUrl = confirmBtn.getAttribute('href');
            const targetForm = confirmBtn.closest('form');

            ConfirmDialogManager.open(message, () => {
                if (targetUrl && targetUrl !== '#') {
                    window.location.href = targetUrl;
                } else if (targetForm) {
                    targetForm.submit();
                }
            });
        }
    });

    const searchInput = document.getElementById('search-input');
    if (searchInput) searchInput.addEventListener('input', filtrarSinais);

    // Favoritar via AJAX para evitar reload e pulo de scroll
    const favForm = document.querySelector('form[action*="/favorites/toggle"]');
    if (favForm) {
        favForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const btn = favForm.querySelector('button');
            const span = btn.querySelector('span');
            const isAdding = !btn.classList.contains('btn-favorited');

            btn.classList.toggle('btn-favorited');
            span.innerText = isAdding ? '★ Adicionado aos Favoritos' : '☆ Adicionar aos Favoritos';

            try {
                const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
                const headers = { 'X-Requested-With': 'XMLHttpRequest' };
                if (csrfToken && csrfHeader) headers[csrfHeader] = csrfToken;

                await fetch(favForm.action, {
                    method: 'POST',
                    body: new FormData(favForm),
                    headers: headers
                });
            } catch (err) {
                console.error("Erro ao favoritar:", err);
                btn.classList.toggle('btn-favorited');
                span.innerText = !isAdding ? '★ Adicionado aos Favoritos' : '☆ Adicionar aos Favoritos';
            }
        });
    }
});