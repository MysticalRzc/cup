//JavaScript代码区域
layui.use('element', function () {
    var element = layui.element;

});

var _hmt = _hmt || [];
(function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?d214947968792b839fd669a4decaaffc";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();

var menuClick = function(menuUrl) {
    top.location.href ="controller/ControllerConfig.html";
};