/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global app, M */

app.initIndex = function () {
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('.mdui-drawer .mdui-list');
        M.Collapsible.init(elems, {});
        var containerElement = document.querySelector('.mdui-container');
        elems[0].onclick = function (event) {
            var eventSrc = event.target;
            var name = eventSrc.classList[eventSrc.classList.length - 1];
            var text = eventSrc.innerText;
            if (eventSrc.classList.contains('nav-item')) {
                var titleElement = containerElement.children[0];
                var mainElement = containerElement.children[1];
                titleElement.innerHTML = text;
                mainElement.innerHTML = app.getPaneContent(name);
                app.initPane({
                    pane: mainElement,
                    url: [name, '/', 'listData', '.', 'do'].join('')
                });
            }
        };
    });
};

