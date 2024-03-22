/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function setWheelValue(endpoint) {
    return fetch(endpoint, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(res => {
        if (res.status === 200) {
        }
    });
}

(function () {
    var $,
            ele,
            container,
            canvas,
            num,
            prizes,
            btn,
            deg = 0,
            fnGetPrize,
            fnGotBack,
            optsPrize;
    var cssPrefix,
            eventPrefix,
            vendors = {
                "": "",
                Webkit: "webkit",
                Moz: "",
                O: "o",
                ms: "ms"
            },
            testEle = document.createElement("p"),
            cssSupport = {};

    Object.keys(vendors).some(function (vendor) {
        if (
                testEle.style[vendor + (vendor ? "T" : "t") + "ransitionProperty"] !==
                undefined
                ) {
            cssPrefix = vendor ? "-" + vendor.toLowerCase() + "-" : "";
            eventPrefix = vendors[vendor];
            return true;
        }
    });

    /**
     * @param  {[type]} name [description]
     * @return {[type]}      [description]
     */
    function normalizeEvent(name) {
        return eventPrefix ? eventPrefix + name : name.toLowerCase();
    }

    /**
     * @param  {[type]} name [description]
     * @return {[type]}      [description]
     */
    function normalizeCss(name) {
        name = name.toLowerCase();
        return cssPrefix ? cssPrefix + name : name;
    }

    cssSupport = {
        cssPrefix: cssPrefix,
        transform: normalizeCss("Transform"),
        transitionEnd: normalizeEvent("TransitionEnd")
    };

    var transform = cssSupport.transform;
    var transitionEnd = cssSupport.transitionEnd;

    // alert(transform);
    // alert(transitionEnd);

    function init(opts) {
        fnGetPrize = opts.getPrize;
        fnGotBack = opts.gotBack;
        opts.config(function (data) {
            prizes = opts.prizes = data;
            num = prizes.length;
            draw(opts);
        });
        events();
    }

    /**
     * @param  {String} id
     * @return {Object} HTML element
     */
    $ = function (id) {
        return document.getElementById(id);
    };

    function draw(opts) {
        opts = opts || {};
        if (!opts.id || num >>> 0 === 0)
            return;

        var id = opts.id,
                rotateDeg = 360 / num / 2 + 90,
                ctx,
                prizeItems = document.createElement("ul"),
                turnNum = 1 / num,
                html = [];

        ele = $(id);
        canvas = ele.querySelector(".hc-luckywheel-canvas");
        container = ele.querySelector(".hc-luckywheel-container");
        btn = ele.querySelector(".hc-luckywheel-btn");

        if (!canvas.getContext) {
            showMsg("Browser is not support");
            return;
        }

        ctx = canvas.getContext("2d");

        for (var i = 0; i < num; i++) {
            ctx.save();
            ctx.beginPath();
            ctx.translate(250, 250); // Center Point
            ctx.moveTo(0, 0);
            ctx.rotate((((360 / num) * i - rotateDeg) * Math.PI) / 180);
            ctx.arc(0, 0, 250, 0, (2 * Math.PI) / num, false); // Radius
            if (i % 2 == 0) {
                ctx.fillStyle = "#ffb820";
            } else {
                ctx.fillStyle = "#ffcb3f";
            }
            ctx.fill();
            ctx.lineWidth = 1;
            ctx.strokeStyle = "#e4370e";
            ctx.stroke();
            ctx.restore();
            var prizeList = opts.prizes;
            html.push('<li class="hc-luckywheel-item"> <span style="');
            html.push(transform + ": rotate(" + i * turnNum + 'turn)">');
            if (opts.mode == "both") {
                html.push("<p id='curve'>" + prizeList[i].text + "</p>");
                html.push('<img src="' + prizeList[i].img + '" />');
            } else if (prizeList[i].img) {
                html.push('<img src="' + prizeList[i].img + '" />');
            } else {
                html.push('<p id="curve">' + prizeList[i].text + "</p>");
            }
            html.push("</span> </li>");
            if (i + 1 === num) {
                prizeItems.className = "hc-luckywheel-list";
                container.appendChild(prizeItems);
                prizeItems.innerHTML = html.join("");
            }
        }
    }

    /**
     * @param  {String} msg [description]
     */
    function showMsg(msg) {
        alert(msg);
    }

    /**
     * @param  {[type]} deg [description]
     * @return {[type]}     [description]
     */
    function runRotate(deg) {
        // runInit();
        // setTimeout(function() {
        container.style[transform] = "rotate(" + deg + "deg)";
        // }, 10);
    }

    /**
     * @return {[type]} [description]
     */
    function events() {
        bind(btn, "click", function () {

            addClass(btn, "disabled");

            var currentUserId = document.getElementById("current-user-id");
            var userId = currentUserId.textContent;
            var quantity = document.getElementById("number-turn");
            var quantityValue = parseInt(quantity.textContent); //số lượt quay còn lại

            if (!hasUserNumberTurnToday(userId)) {
                saveUserNumberTurning(userId);
            }
            quantityValue--;
            if (quantityValue < 0) {
                return;
            }
            quantity.textContent = quantityValue;

            fnGetPrize(function (data) {
                if (data[0] == null && !data[1] == null) {
                    return;
                }
                optsPrize = {
                    prizeId: data[0],
                    chances: data[1]
                };
                deg = deg || 0;
                deg = deg + (360 - (deg % 360)) + (360 * 10 - data[0] * (360 / num));
                runRotate(deg);
            });
            bind(container, transitionEnd, eGot);
        });
    }

    function hasUserNumberTurnToday(userId) {
        const key = `number_turn_${userId}`;
        const lastNumberTurnTimestamp = localStorage.getItem(key);
        if (lastNumberTurnTimestamp) {
            const lastNumberTurnDate = new Date(Number(lastNumberTurnTimestamp));
            const today = new Date();
            return (
                    lastNumberTurnDate.getDate() === today.getDate() &&
                    lastNumberTurnDate.getMonth() === today.getMonth() &&
                    lastNumberTurnDate.getFullYear() === today.getFullYear()
                    );
        }
        return false;
    }

    function saveUserNumberTurning(userId) {
        const key = `number_turn_${userId}`;
        localStorage.setItem(key, Date.now());
    }

    function eGot() {
        if (optsPrize.chances == null) {
            return fnGotBack(null);
        } else {
            removeClass(btn, "disabled");
            return fnGotBack(prizes[optsPrize.prizeId].text);
        }
    }

    /**
     * Bind events to elements
     * @param {Object}    ele    HTML Object
     * @param {Event}     event  Event to detach
     * @param {Function}  fn     Callback function
     */
    function bind(ele, event, fn) {
        if (typeof addEventListener === "function") {
            ele.addEventListener(event, fn, false);
        } else if (ele.attachEvent) {
            ele.attachEvent("on" + event, fn);
        }
    }

    /**
     * hasClass
     * @param {Object} ele   HTML Object
     * @param {String} cls   className
     * @return {Boolean}
     */
    function hasClass(ele, cls) {
        if (!ele || !cls)
            return false;
        if (ele.classList) {
            return ele.classList.contains(cls);
        } else {
            return ele.className.match(new RegExp("(\\s|^)" + cls + "(\\s|$)"));
        }
    }

    // addClass
    function addClass(ele, cls) {
        if (ele.classList) {
            ele.classList.add(cls);
        } else {
            if (!hasClass(ele, cls))
                ele.className += "" + cls;
        }
    }

    // removeClass
    function removeClass(ele, cls) {
        if (ele.classList) {
            ele.classList.remove(cls);
        } else {
            ele.className = ele.className.replace(
                    new RegExp(
                            "(^|\\b)" + className.split(" ").join("|") + "(\\b|$)",
                            "gi"
                            ),
                    " "
                    );
        }
    }

    var hcLuckywheel = {
        init: function (opts) {
            return init(opts);
        }
    };

    window.hcLuckywheel === undefined && (window.hcLuckywheel = hcLuckywheel);

    if (typeof define == "function" && define.amd) {
        define("HellCat-Luckywheel", [], function () {
            return hcLuckywheel;
        });
    }
})();

////////////////////////////////////////////////////////////////////////////////

var isPercentage = true;
var prizes = [];
document.addEventListener(
        "DOMContentLoaded",
        function () {
            var chances = null;
            const endpoint = document.getElementById("api-wheel-of-forture").href;
            fetch(endpoint, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/xml'
                }
            }).then(res =>
                res.json()
            ).then(data => {
                for (let d of data) {
                    prizes.push({
                        text: d.note,
                        img: d.img,
                        number: d.quantity,
                        code: d.code,
                        percentpage: d.percentpage // 0.01%
                    });
                }
                hcLuckywheel.init({
                    id: "luckywheel",
                    config: function (callback) {
                        callback &&
                                callback(prizes);
                    },
                    mode: "both",
                    getPrize: function (callback) {
                        var rand = randomIndex(prizes);
                        chances = rand;
                        callback && callback([rand, chances]);
                    },
                    gotBack: function (data) {
                        if (data === null) {
                            Swal.fire('Chương trình kết thúc', 'Đã hết phần thưởng', 'error')
                        } else if (data === 'Chúc bạn may mắn lần sau') {
                            Swal.fire('Bạn không trúng thưởng', data, 'info');
                        } else {
                            Swal.fire({
                                icon: "success",
                                title: data,
                                html: `<span class="m-2 text-danger">${prizes[chances].code}</span><a class="copy-button"><i class="fa-regular fa-copy"></i></a>`,
                                imageUrl: prizes[chances].img,
                                imageHeight: 200,
                                footer: "Chúc mừng bạn đã trúng giải thưởng",
                                didOpen: () => {
                                    // Lấy chuỗi cần copy
                                    const textToCopy = prizes[chances].code;

                                    // Cấu hình Clipboard.js
                                    const clipboard = new ClipboardJS('.copy-button', {
                                        text: () => textToCopy
                                    });

                                    // Xử lý sự kiện khi copy thành công
                                    clipboard.on('success', (e) => {
                                        e.clearSelection();
                                        Swal.fire("Đã sao chép!", "", "success");
                                    });

                                    // Xử lý sự kiện khi copy thất bại
                                    clipboard.on('error', () => {
                                        Swal.fire("Sao chép thất bại. Hãy thử lại!", "", "error");
                                    });
                                }
                            });
                        }
                    }
                });
            },
                    false
                    );
        });
function randomIndex(prizes) {
    if (isPercentage) {
        var counter = 1;
        for (let i = 0; i < prizes.length; i++) {
            if (prizes[i].number === 0) {
                counter++;
            }
        }
        if (counter === prizes.length) {
            return null;
        }
        let rand = Math.random();
        let prizeIndex = null;
        let cumulativePercentage = 0;
        for (let i = 0; i < prizes.length; i++) {
            cumulativePercentage += prizes[i].percentpage;

            if (rand < cumulativePercentage) {
                prizeIndex = i;
                if (prizes[prizeIndex].number !== 0) {
                    prizes[prizeIndex].number--;
                    return prizeIndex;
                } else {
                    return randomIndex(prizes);
                }
            }
        }
    } else {
        var counter = 0;
        for (let i = 0; i < prizes.length; i++) {
            if (prizes[i].number === 0) {
                counter++;
            }
        }
        if (counter === prizes.length) {
            return null;
        }
        var rand = (Math.random() * (prizes.length)) >>> 0;
        if (prizes[rand].number !== 0) {
            prizes[rand].number = prizes[rand].number - 1;
            return rand;
        } else {
            return randomIndex(prizes);
        }
    }
}