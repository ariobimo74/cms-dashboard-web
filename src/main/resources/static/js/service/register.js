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

    })

    async function generateRecommendedUsername() {
        console.log("recommendedUsername = " + JSON.stringify(recommendedUsername))
        $('#recommendedUsername').empty()
        $('#recommendedUsername').append("<span>This is recommended username for login. please select one of them</span>")
                
        for (let i = 0; i < recommendedUsername.length; i++) {
            $('#recommendedUsername').append('<button type="button" name="' + recommendedUsername[i] + '" class="btn btn-outline-primary">' + recommendedUsername[i] + '</button>')
        }
        $('#recommendedUsername').toggle()         
    }

    $(document).on('click', '#recommendedUsername .btn', function() {
        var value = $(this).attr('name');
        $('#username').val(value);
    });
})