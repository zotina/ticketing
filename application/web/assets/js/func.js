function validateForm(event) {
    const form = event.target; 
    const inputs = form.querySelectorAll('input'); 

    const emptyInputs = [];
    const invalidInputs = []; 
    const optionalInputs = ['id_client','id_service'];

    inputs.forEach(input => {
        if (!input.value.trim()) { 
            if (!optionalInputs.includes(input.id)) { 
                emptyInputs.push(input); 
            }
        } else {
            const type = input.getAttribute('type'); 
            if (!isValidType(input.value.trim(), type)) { 
                invalidInputs.push(input); 
            } else {
                const errorMessage = input.parentElement.querySelector('.message-erreur');
                if (errorMessage) {
                    errorMessage.textContent = ''; 
                }
            }
        }
    });

    emptyInputs.forEach(input => {
        const errorMessage = input.parentElement.querySelector('.message-erreur');
        if (errorMessage) {
            errorMessage.textContent = `Le champ ${input.name} est requis`; // Affiche le nom du champ vide à côté du message d'erreur
        }
    });

    invalidInputs.forEach(input => {
        const errorMessage = input.parentElement.querySelector('.message-erreur');
        if (errorMessage) {
            errorMessage.textContent = `Le champ ${input.name} est invalide`; // Affiche le nom du champ invalide à côté du message d'erreur
        }
    });

    if (emptyInputs.length > 0 || invalidInputs.length > 0) {
        event.preventDefault(); // Empêche la soumission par défaut du formulaire si des champs sont vides ou invalides
    }
}

function isValidType(value, type) {
    switch (type) {
        case 'number':
            return !isNaN(value); // Vérifie si la valeur est un nombre
        case 'email':
            return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value); // Vérifie si la valeur est une adresse email valide
        case 'url':
            try {
                new URL(value);
                return true; // Vérifie si la valeur est une URL valide
            } catch {
                return false;
            }
        // Ajoutez d'autres types d'inputs si nécessaire
        default:
            return true; // Par défaut, considère que la valeur est valide
    }
}
  

document.addEventListener('DOMContentLoaded', () => {
    const forms = document.querySelectorAll('form'); // Sélectionne tous les formulaires sur la page
    forms.forEach(form => {
        form.addEventListener('submit', validateForm); // Ajoute un écouteur d'événement de soumission à chaque formulaire
    });
});
