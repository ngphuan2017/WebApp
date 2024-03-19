function copyCode(index) {
    const textToCopy = document.getElementsByClassName('discountCode')[index].innerText;

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