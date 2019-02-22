<script src='${ctx}/resources/admin/assets/js/jquery-2.0.3.min.js'></script>
<script src="${ctx}/resources/admin/assets/js/bootstrap.min.js"></script>
<script src="${ctx}/resources/admin/assets/js/typeahead-bs2.min.js"></script>
<script src="${ctx}/resources/admin/assets/js/ace-elements.min.js"></script>
<script src="${ctx}/resources/admin/assets/js/ace.min.js"></script>

<script>
    function loadPage(path) {
        $('.active').removeClass('active');
        var element = $(window.event.target).parent()[0];
        if(element.localName == 'a'){
            $(window.event.target).parent().parent().attr('class', 'active');
        }
        if(element.localName == 'li'){
            $(window.event.target).parent().attr('class', 'active');
        }

        $('#boom').text(element.innerText);

        var base = $('#base').attr('href');
        $('#sqtFrame').attr("src", base + path);
    }

    function changeFrameHeight() {
        $("#sqtFrame") && $("#sqtFrame").height(document.documentElement.clientHeight - 100);
    }

    window.onresize = function () {
        changeFrameHeight();
    }
    $(function () {
        changeFrameHeight();
    });
</script>
