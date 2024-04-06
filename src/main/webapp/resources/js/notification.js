function copyCode(id) {
    const textToCopy = document.getElementById(`discount-code-${id}`);

    const clipboard = new ClipboardJS('.copy-button', {
        text: () => textToCopy
    });

    clipboard.on('success', (e) => {
        e.clearSelection();
        Swal.fire("Đã sao chép!", "", "success");
    });

    clipboard.on('error', () => {
        Swal.fire("Sao chép thất bại. Hãy thử lại!", "", "error");
    });
}

function copyWheelCode(id) {
    const textToCopy = document.getElementById(`wheel-code-${id}`);

    const clipboard = new ClipboardJS('.copy-button', {
        text: () => textToCopy
    });

    clipboard.on('success', (e) => {
        e.clearSelection();
        Swal.fire("Đã sao chép!", "", "success");
    });

    clipboard.on('error', () => {
        Swal.fire("Sao chép thất bại. Hãy thử lại!", "", "error");
    });
}