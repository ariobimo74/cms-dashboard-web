$('document').ready(function() {
    console.log("register")
    var roles = []
    var genders = []
    var members = []
    var recommendedUsername = []

    generateCommonValues()

    async function generateCommonValues() {
        await $.ajax({
            url: '/api/v1/value/gender',
                type: 'get',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(result) {
                    genders = result.data
                }
        })
    
        await $.ajax({
            url: '/api/v1/value/member',
                type: 'get',
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(result) {
                    members = result.data
                }
        })

        await $.ajax({
            url: '/api/v1/role',
            type: 'get',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                roles = result.data
            }
        })

        await determineCommonValues()
    }

    async function determineCommonValues() {
        if (roles.length > 0) {
            $('#role').empty()
            for (let i = 0; i < roles.length; i++) {
                $('#role').append('<option value="' + roles[i].id + '">' + roles[i].roleName + '</option>')
            }
        }
        
        if (members.length > 0) {
            $('#member').empty()
            for (let i = 0; i < members.length; i++) {
                $('#member').append('<option value="' + members[i].value + '">' + members[i].value + '</option>')
            }
        }

        if (genders.length > 0) {
            $('#gender').empty()
            for (let i = 0; i < genders.length; i++) {
                $('#gender').append('<option value="' + genders[i].value + '">' + genders[i].value + '</option>')
            }
            $('#role')
        }
    }

    $('#firstName').on('change', function() {
        getRecommendedUsername()
    })
    $('#birthDate').on('change', function() {
        getRecommendedUsername()
    })

    async function getRecommendedUsername() {
        let firstName = $('#firstName').val()
        let lastName = $('#lastName').val()
        let dateBirth = $('#birthDate').val()
        console.log('birthDate = ' + dateBirth)

        var year = dateBirth.split('-')[0]
        console.log("year = " + year)

        if (firstName.length > 0 && dateBirth.length == 10 && parseInt(year) > parseInt(1900)) {
            var requestBodyToRecommendedUsername = {
                name: firstName + " " + lastName,
                birthDate: dateBirth,
                email: $('#email').val(),
                idCardNumber: $('#idCardNumber').val(),
                mobilePhoneNumber: $('#phoneNumber').val()
            }

            await $.ajax({
                url: '/api/v1/value/recommended-username',
                type: 'patch',
                data: JSON.stringify(requestBodyToRecommendedUsername),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(result) {
                    recommendedUsername = result.data
                }
            })

            if (recommendedUsername.length > 0) {
                await generateRecommendedUsername()
            }
        } else $('#recommendedUsername').empty()
    }

    $('#registerForm').submit(function() {
        event.preventDefault()

        if ($('#firstName').val() == "" || $('#idCardNumber').val() == "" || $('#birthDate').val() == "" || $('#birthPlace').val() == ""
            || $('#gender').val() == "" || $('#username').val() == "" || $('#password').val() == "" || $('#confirmPassword').val() == "" 
            || $('#role').val() == "") {
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
                if (value) executeRegister()
            }, "warning")
        }
    )
    
    async function executeRegister() {
        let registerRequest = {
            name : $('#firstName').val() + " " + $('#lastName').val(),
            idCardNumber : $('#idCardNumber').val(),
            dateOfBirth : $('#birthDate').val(),
            placeOfBirth : $('#birthPlace').val(),
            gender : $('#gender').val(),
            username : $('#username').val(),
            password : $('#password').val(),
            confirmPassword : $('#confirmPassword').val(),
            roleDto : {
                id : $('#role').val(),
                roleName : $('#role option:selected').text()
            },
            mobilePhoneNumber : $('#phoneNumber').val(),
            email : $('#email').val(),
            memberLevel : $('#member').val()
        }

        await $.ajax({
            url: '/api/v1/register',
            type: 'post',
            data: JSON.stringify(registerRequest),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                baseResponse = result

                if (baseResponse.responseData.responseCode == 200) {
                    $('#firstName').val('')
                    $('#lastName').val('')
                    $('#idCardNumber').val('')
                    $('#birthDate').val('')
                    $('#birthPlace').val('')
                    $('#gender').val('')
                    $('#username').val('')
                    $('#password').val('')
                    $('#confirmPassword').val('')
                    $('#role').val('')
                    $('#phoneNumber').val('')
                    $('#email').val('')
                    $('#member').val('')

                    swal({
                            title: "Successfully Registered!",
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

    async function generateRecommendedUsername() {
        console.log("recommendedUsername = " + JSON.stringify(recommendedUsername))
        $('#recommendedUsername').empty()
        $('#recommendedUsername').append("<span>This is recommended username for login. please select one of them</span>")
                
        for (let i = 0; i < recommendedUsername.length; i++) {
            $('#recommendedUsername').append('<button type="button" name="' + recommendedUsername[i] + '" class="btn btn-outline-primary">' + recommendedUsername[i] + '</button>')
        }
        $('#recommendedUsername').show()
    }

    $(document).on('click', '#recommendedUsername .btn', function() {
        var value = $(this).attr('name');
        $('#username').val(value);
    });
})