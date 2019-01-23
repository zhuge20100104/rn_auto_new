if (typeof BDD_jFilter === 'undefined') {
    BDD_jFilter = function (e) {
        var t = [];
        for (var n = 0; n < e.length; ++n) {
            if (typeof e[n] !== 'undefined' && e[n] !== undefined
                && e[n].offsetWidth > 1) {
                e[n].jFind = function (e) {
                    var t = $(this).find(e);
                    return BDD_jFilter(t)
                };
                t.push(e[n]);
            }
        }
        return $(t);
    }
}

if (typeof BDD_jQuery === 'undefined') {
    BDD_jQuery = function (e) {
        return BDD_jFilter($(e));
    }
}
if (typeof BDD_jFindByAttr === 'undefined') {
    BDD_jFindByAttr = function (selector, attr, value) {
        var ele = selector + "[" + attr + "='" + value + "']";
        return $(ele);
    }
}
if (typeof BDD_jFindBySelector === 'undefined') {
    BDD_jFindBySelector = function (selector) {
        return $(selector);
    }
}
if (typeof BDD_GenId === 'undefined') {
    /**
     * @return {string}
     */
    BDD_GenId = function (e) {
        var id = e.id;
        var key = 'genkey_';
        var d = new Date();
        if (id === 'undefined' || id === "" || id === null) {
            var t = d.getTime();
            id = e.setAttribute('id', key + t);
        }
        return e.id;
    }
}

//---------------------------------
BDD_jFilter = function (e) {
    var t = [];
    for (var n = 0; n < e.length; ++n) {
        if (typeof e[n] !== 'undefined' && e[n] !== undefined && e[n].offsetWidth > 1) {
            e[n].jFind = function (e) {
                var t = $(this).find(e);
                return BDD_jFilter(t)
            };
            t.push(e[n]);
        }
    }
    return $(t);
}