var a_idx = 0;
 
$("body").click(function(e) {
    var a = new Array
        ("酱油", "喜欢", "糖油",
        "还有", "奶油", "2020",
        "我们" ,"一起", "加油");
    var $i = $("<span/>").text(a[a_idx]);
    a_idx = (a_idx + 1) % a.length;
    var x = e.pageX,
    y = e.pageY;
    $i.css({
        "z-index": 144469,
        "top": y - 20,
        "left": x,
        "position": "absolute",
        "font-weight": "bold",
        "color": "#f00"
    });
    $("body").append($i);
    $i.animate({
        "top": y - 180,
        "opacity": 0
    },
    1500,
    function() {
        $i.remove()
    })
});