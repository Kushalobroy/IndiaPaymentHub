function showToast(type, message, position = 'top-right', duration = 3000) {
    // Check if toast container exists; if not, create one
    let container = document.querySelector(`.toast-container.${position}`);
    if (!container) {
        container = document.createElement("div");
        container.className = `toast-container ${position}`;
        document.body.appendChild(container);
    }

    // Create the toast element
    let toast = document.createElement("div");
    toast.className = `toast ${type} show`;
    toast.textContent = message;

    // Append toast to container
    container.appendChild(toast);

    // Remove toast after duration
    setTimeout(() => {
        toast.classList.add("hide");
        setTimeout(() => container.removeChild(toast), 500);
    }, duration);
}
