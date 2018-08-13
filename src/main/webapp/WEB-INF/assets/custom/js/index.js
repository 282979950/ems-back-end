/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global app, M */

app.initIndex = function () {
    document.addEventListener('DOMContentLoaded', function () {
        M.Slider.init(document.querySelectorAll('.slider'), {
            //indicators: false,
            height: 250,
            interval: 3000
        });
        var elems = document.querySelectorAll('.collapsible');
        M.Collapsible.init(elems, {});
        var tabsElement = document.querySelector('.tabs'), panesElement = document.querySelector('.panes');
        var instance = M.Tabs.init(tabsElement, {
            swipeable: true
        });
        elems[0].onclick = function (event) {
            var eventSrc = event.target;
            var name = eventSrc.classList[eventSrc.classList.length - 1], paneId = [name, '-', 'pane'].join(''), text = eventSrc.innerHTML;
            if (eventSrc.classList.contains('nav-item')) {
                if (document.getElementById(paneId)) {
                    instance.select(paneId), instance.updateTabIndicator();
                } else {
                    var tabElement = tabsElement.getElementsByClassName('tab')[0].cloneNode(true);
                    var childElement = tabElement.children[0];
                    childElement.href = '#' + paneId, childElement.innerHTML = text;
                    tabsElement.appendChild(tabElement);
                    var paneElement = panesElement.getElementsByClassName('pane')[0].cloneNode(true);
                    paneElement.id = [name, '-', 'pane'].join(''), paneElement.innerHTML = app.getPaneContent(name);
                    panesElement.appendChild(paneElement), app.initPane(paneElement);
                    instance.destroy(), instance = M.Tabs.init(tabsElement, {swipeable: true});
                    instance.select(paneId), instance.updateTabIndicator();
                }
            }
        };
    });
};

