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
            { data: 'memberLevel' },
            {
                data: 'id', 
                render: function (data, type, row) {
                    return `
                        <button class="btn btn-primary detail-btn" title="View Detail" data-id="${data}"><i class="fa fa-fw" aria-hidden="true">&#xf15c</i></button>
                        <button class="btn btn-warning edit-btn" title="Edit Data" data-id="${data}"><i class="fa fa-fw" aria-hidden="true" >&#xf044</i></button>
                        <button class="btn btn-danger delete-btn" title="Delete Data" data-id="${data}"><i class="fa fa-fw" aria-hidden="true" >&#xf1f8</i></button>
                    `;
                },
                orderable: false, 
                searchable: false
            }
        ]
    })

    $('#user-dataTables').on('click', '.detail-btn', function () {
        window.location.href = "/profile/" + $(this).data('id')
    })

    $('#user-dataTables').on('click', '.edit-btn', function () {
        window.location.href = "/edit/" + $(this).data('id')
    })
})