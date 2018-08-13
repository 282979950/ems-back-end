/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global app, M */
app.initLogin = function () {
    document.getElementsByClassName('login-btn')[0].onclick = function () {
        var helperElements = document.querySelectorAll('.login-container .helper-text');
        for (var i = 0, limit = helperElements.length; i < limit; i++) {
            var helperElement = helperElements[i];
            var afterStyle = window.getComputedStyle(helperElement, ':after');
            if (afterStyle.content === 'none') {
                return false;
            }
        }
        $.ajax({
            type: 'POST',
            url: 'login.action',
            contentType: 'application/json;charset=utf-8',
            context: document.body,
            data: JSON.stringify({
                username: document.getElementById('username').value,
                password: document.getElementById('password').value
            }),
            beforeSend: function (xhr) {
                xhr.withCredentials = true;
            },
            success: function (response) {
                console.log(response);
                M.toast({
                    html: response.message,
                    classes: 'rounded repaint-toast'
                });
                setTimeout(function () {
                    window.location.href = 'index.html';
                }, 2000);
            },
            error: function (xhr, statusText, error) {
                M.toast({
                    html: '错误,状态码:' + statusText,
                    classes: 'rounded repaint-toast'
                });
            }
        });
    };

};

