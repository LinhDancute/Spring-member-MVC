const messageToast = document.getElementById('message-toast');

const showToast = content => {
    const toastBody = messageToast.querySelector('.toast-body');
    toastBody.textContent = content;
    const bsToast = new bootstrap.Toast(messageToast);
    bsToast.show();
};

document.getElementById('registerForm').addEventListener('submit', event => {
    const formControls = $('.form-control');
    const hasNullValue = Array.from(formControls).some(element => {
        element.focus();
        return !element.value;
    });
    if (hasNullValue) {
        event.preventDefault();
        showToast("Vui lòng điền đầy đủ thông tin !!!");
    }
});

const existedMSSV = params.get('existedMSSV');
if(existedMSSV) {
    showToast("MSSV đã được sử dụng.");
}
const existedEmail = params.get('existedEmail');
if(existedEmail) {
    showToast("Email đã được sử dụng.");
}

const success = params.get('successRegister');
if(success) {
    showToast("Đăng ký thành công.");
    setTimeout(function() {
        window.location.href = "/";
    }, 2000);
}