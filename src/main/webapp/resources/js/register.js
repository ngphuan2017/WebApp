Validator.isRequired = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            return value ? undefined :  message || 'Vui lòng nhập lại mật khẩu'
        }
    };
}

Validator.isEmail = function (selector, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
            return regex.test(value) ? undefined :  message || 'Vui lòng nhập email';
        }
    };
}

Validator.minLength = function (selector, min, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}$/;
            return regex.test(value) ? undefined :  message || 'Mật khẩu của bạn tối thiểu 8 ký tự, ít nhất 1 chữ cái và 1 số';

        }
    };
}

Validator.isConfirmed = function (selector, getConfirmValue, message) {
    return {
        selector: selector,
        test: function (value) {
            return value === getConfirmValue() ? undefined : message || 'Giá trị nhập vào không chính xác';
        }
    }
}
Validator.isname = function (selector, min, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^[a-z](?=.*[0-9]).{4,}$/i;
            return regex.test(value) ? undefined :  message || 'Tên đăng nhập của bạn tối thiểu 4 ký tự và ít nhất 1 số';

        }
    };
}
Validator.isdienthoai = function (selector, min, message) {
    return {
        selector: selector,
        test: function (value) {
            var regex = /^[0-9].{9,}$/;
            return regex.test(value) ? undefined :  message || 'Điện thoại của bạn chưa chính xác';

        }
    };
}