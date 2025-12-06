$(document).ready(function() {
    generateMenu()

    let responseData = {
        acknowledge : "",
        responseCode : 0,
        responseMessage : ""
    }

    async function generateMenu() {
        await $.ajax({
            url: '/api/v1/menu',
            type: 'get',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function(result) {
                let menuResponse = {
                    data : null,
                    responseData : responseData
                }
                menuResponse = result

                if (menuResponse.responseData.responseCode == 200) {
                    $('#greetingName').text("Hello, " + result.data.greetingName + '!')
                    
                    if (parseInt(result.data.menus.length) > parseInt(0)) {
                        for (i = 0; i < result.data.menus.length; i++) {
                            let menuTemp = result.data.menus[i]

                            $('#side-menu').append('<li class="class-' + menuTemp.menuName + ' active"><a href=' + menuTemp.url + '><i class="' + menuTemp.iconClass + '"></i> ' + menuTemp.menuName + '</a>')
                            if (menuTemp.subMenu.length > 0) {
                                $(".class-" + menuTemp.menuName).append('<ul class="nav nav-second-level collapse in" aria-expanded="true" style="">')
                                for (let j = 0; j < menuTemp.subMenu.length; j++) {
                                    let menuTemp2 = menuTemp.subMenu[j]
                                    $('.nav-second-level').append('<li><a href=' + menuTemp2.url + '><i class="' + menuTemp2.iconClass + '"></i> ' + menuTemp2.menuName + '</a></li>')
                                }
                                $(".class-" + menuTemp.menuName).append('</ul>')
                            }
                            
                            $('#side-menu').append('</li>')
                        }
                    }
                }
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
})