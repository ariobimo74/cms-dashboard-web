$(document).ready(function() {
    console.log("view detail")

    generateContentForForm()
    changeHtmlType()
    makeInputsReadonly()
    getProfileData()

    async function getProfileData() {
        let profileId = window.location.pathname.replace("profile/", "")
        await $.ajax({
            url: '/api/v1/user/profile' + profileId,
            type: 'get',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                baseResponse = result
                let resultData = result.data

                if (baseResponse.responseData.responseCode == 200) {
                    $('#firstName').val(resultData.firstName)
                    $('#lastName').val(resultData.lastName)
                    $('#idCardNumber').val(resultData.idCardNumber)
                    $('#birthDate').val(resultData.dateOfBirth)
                    $('#birthPlace').val(resultData.placeOfBirth)
                    $('#gender').val(resultData.gender)
                    $('#username').val(resultData.username)
                    $('#role').val(resultData.roleDto.roleName)
                    $('#phoneNumber').val(resultData.mobilePhoneNumber)
                    $('#email').val(resultData.email)
                    $('#member').val(resultData.memberLevel)
                }
                else swal(result.responseData.acknowledge, result.responseData.responseMessage, "error")
            }, 
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("jqXHR = ", jqXHR)
                console.log("status = ", jqXHR.status)
                console.log("textStatus = ", textStatus)

                baseResponse = jqXHR.responseJSON
                
                if (parseInt(jqXHR.status) == parseInt(404)) window.location.href = "/error/404"
                swal(baseResponse.responseData.acknowledge, baseResponse.responseData.responseMessage, "error")
            }
        })
    }

    function changeHtmlType() {
        $('#genderFormGroup').empty().append(`
            <label>Gender</label>
            <input type="text" class="form-control" id="gender" name="gender" >
        `)

        $('#roleFormGroup').empty().append(`
            <label>Role</label>
            <input type="text" class="form-control" id="role" name="role" >
        `)

        $('#memberFormGroup').empty().append(`
            <label>Member</label>
            <input type="text" class="form-control" id="member" name="member" >
        `)
    }

    function makeInputsReadonly() {
        $('form :input').each(function() {
            $(this).prop('readonly', true);
        });
    }
})