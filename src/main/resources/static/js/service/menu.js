$(document).ready(function() {
    $('#side-menu').css({
        background: 'aliceblue'
    })

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

                            if (menuTemp.subMenu.length > 0) {
                                $('#side-menu').append('<li class="class-' + menuTemp.menuClass + ' active"><a href=' + menuTemp.url + ' style="cursor: context-menu"><i class="' + menuTemp.iconClass + '"></i> ' + menuTemp.menuName + '</a>')
                                $(".class-" + menuTemp.menuClass).append('<ul class="' + menuTemp.menuClass + '-second ' + 'nav nav-second-level collapse in" aria-expanded="true" style="">')
                                for (let j = 0; j < menuTemp.subMenu.length; j++) {
                                    let menuTemp2 = menuTemp.subMenu[j]
                                    $('.' + menuTemp.menuClass + '-second').append('<li><a href=' + menuTemp2.url + '><i class="' + menuTemp2.iconClass + '"></i> ' + menuTemp2.menuName + '</a></li>')
                                }
                                $(".class-" + menuTemp.menuClass).append('</ul>')
                            } else $('#side-menu').append('<li class="class-' + menuTemp.menuClass + ' active"><a href=' + menuTemp.url + '><i class="' + menuTemp.iconClass + '"></i> ' + menuTemp.menuName + '</a>')
                            
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