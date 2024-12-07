$(document).ready(function () {
    $('#user-dataTables').DataTable({
        responsive: true,
        processing: true,
        serverSide: true,
        ajax: {
            url: '/api/v1/user/profiles-data-table',
            data: function (d) {
                console
                return $.extend({}, d, {
                    search: d.search.value 
                });
            },
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
        },
        columns: [
            { data: 'name' },
            { data: 'idCardNumber' },
            { data: 'dateOfBirth' },
            { data: 'placeOfBirth' },
            { data: 'gender' },
            { data: 'username' },
            { data: 'roleDto.roleName' },
            { data: 'mobilePhoneNumber' },
            { data: 'email' },
            { data: 'memberLevel' }
        ]
    });
});