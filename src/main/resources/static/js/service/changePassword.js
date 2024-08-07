$(document).ready(function(){
    console.log("change password")
    var currentRoles = []
    var allowedUserToChangePassword = []
    roleToChangePassword()

    async function roleToChangePassword() {
        console.log("determine roles")
        await $.ajax({
            url: '/v1/user/current-role',
                type: 'get',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(result) {
                    currentRoles = result.data
                }
        })
    
        await $.ajax({
            url: '/v1/user/allowed-user-to-change-password',
                type: 'get',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(result) {
                    allowedUserToChangePassword = result.data
                }
        })

        await permissionForUsername()
    }

    async function permissionForUsername() {
        if (currentRoles.length > 0 && allowedUserToChangePassword.length > 0 
            && currentRoles.some(data => allowedUserToChangePassword.includes(data))) 
            $('#userNameGroup').toggle()
    }

    $('#forgotPasswordForm').submit(function() {
        event.preventDefault()

        if ($('#oldPassword').val() == "" || $('#oldPassword').val() == null 
        || $('#newPassword').val() == "" || $('#newPassword').val() == null 
        || $('#confirmNewPassword').val() == "" || $('#confirmNewPassword').val() == null ) {
            swal("Attention!", "Please insert the reqired fields!", "info")
            return false
        }

        swal({
            title: "Are you sure, you want to change your password?",
            text: "",
            icon: "warning",
            buttons: true,
            dangerMode: true,
          }).then((value) => {
            if (value) executeChangePassword()
        }, "warning")
    })

    function executeChangePassword() {
        let requestBodyChangePassword = {
            username: $('#username').val(),
            oldPassword: $('#oldPassword').val(),
            newPassword: $('#newPassword').val(),
            confirmNewPassword: $('#confirmNewPassword').val()
        }

        $.ajax({
            url: '/v1/user/change-password',
            data: JSON.stringify(requestBodyChangePassword),
            type: 'post',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                baseResponse = result

                if (baseResponse.responseData.responseCode == 200) {
                    $('#username').val('')
                    $('#oldPassword').val('')
                    $('#newPassword').val('')
                    $('#confirmNewPassword').val('')

                    swal({
                            title: "Successfully saved your password",
                            icon: "success",
                            closeOnClickOutside: true,
                            closeOnEsc: true,
                            button: "Redirect to Home",
                            onClose: function () {
                                window.location.href = "/dashboard"
                            }
                        }).then((value) => {
                            if (value) window.location.href = "/dashboard"
                        })
                    }
                else swal(result.responseData.acknowledge, result.responseData.responseMessage, "error")
            }, 
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("jqXHR = ", jqXHR)
                console.log("status = ", jqXHR.status)
                console.log("textStatus = ", textStatus)

                baseResponse = jqXHR.responseJSON
                
                swal(baseResponse.responseData.acknowledge, baseResponse.responseData.responseMessage, "error")
            }
        })
    }

    $('#cancelBtn').click(function() {
        if (window.history.length > 1) window.history.back()
        else window.location.href = "/dashboard"
    })
})
