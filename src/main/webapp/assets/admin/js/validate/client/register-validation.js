function validateForm(e, context){
    let noError = true;
    e.preventDefault()
    let passMatch = $('#confirmPasswordNotMatch')
    let passValidate = $('#passwordValidateMessage')
    let password = $('#password')
    let confirmPassword = $('#confirmPassword')
    let newPassword = $('#newPassword')
    let textNewPass = $('#newPasswordText')
    let userId = $('#userId');
    const pattern = /^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,30}$/;
    console.log("Mật khẩu yếu")
    if (pattern.test(password.val()) === false){
        passValidate.html('Mật khẩu yếu').css("color", 'red')
        console.log("Mật khẩu yếu")

        noError = false;
    }
    if(userId.length === 0) {
        if (password.val() !== confirmPassword.val()) {
            passMatch.html('Mật khẩu không khớp').css('color', 'red');
            noError = false;
        } else {
            passMatch.html('')
        }
    }else if(newPassword.val() !== ""){
        console.log(newPassword.length)
        if (confirmPassword != null && newPassword.val() !== confirmPassword.val()) {
            passMatch.html('Mật khẩu không khớp').css('color', 'red');
            noError = false;
        } else {
            passMatch.html('')
        }

        if (pattern.test(password.val()) === false){
            passValidate.html('Mật khẩu yếu').css("color", 'red')
            console.log("Mật khẩu yếu")

            noError = false;
        }
        if (pattern.test(newPassword.val()) === false){
            console.log(newPassword)
            textNewPass.html('Mật khẩu yếu').css("color", 'red')
            console.log("Mật khẩu yếu")
            noError = false;
        }
    }
    let url = context;
    if(userId.length === 0){
        url += `/users/check-add`
    }else{
        url += `/users/check-edit`
    }
    $.ajax({
        url: url,
        method: "POST",
        data: {
            'username': $('#username').val(),
            'email': $('#email').val(),
            'phone': $('#phone').val(),
            'password': password.val(),
            'userId': userId.val() === ''  ? 0 : userId.val(),
            'hasChangePass': newPassword.val() !== '' || confirmPassword.val() !== ''
        },
        async: false,
        success: function (data){
            console.log(data)
            let str = data.toString()
            let arr = str.substring(0, str.lastIndexOf(']')).replace('[','').replace(']','').split(', ');
            console.log(arr)
            if (arr.includes('user')) {
                $('#userValidateMessage').html('Tên tài khoản đã tồn tại').css('color','red')
                noError = false;
            } else {
                $('#userValidateMessage').html('')
            }

            if (arr.includes('email')) {
                $('#emailValidateMessage').html('Email đã tồn tại').css('color','red')
                noError = false;
            } else {
                $('#emailValidateMessage').html('')
            }

            if (arr.includes('phone')) {
                $('#phoneValidateMessage').html('Số điện thoại đã tồn tại').css('color','red')
                noError = false;
            } else {
                $('#phoneValidateMessage').html('')
            }

            if (arr.includes('password')) {
                $('#passwordValidateMessage').html('Mật khẩu sai').css('color','red')
                noError = false;
            } else {
                $('#passwordValidateMessage').html('')
            }
        },
        error: function (error){
            noError = false;
        }
    })
    if(noError){
        $('#form-add').unbind('submit').submit();
    }
}