TreeLoader = {
    loadLibTree : function(type,libTreeId,shelfTreeId){
      $.get("/library/tree",{"use":type},function(data,status){
        var root = new Object();
        root.name = "图书馆列表";
        root.isParent = true;
        root.open = true;
//        root.chkDisabled = true;

        var treeData = new Array();
        $.each(data,function(index,ele){
            var lib = new Object();
            lib.id = ele.id;
            lib.name = ele.name;
            lib.open = true;
            lib.isParent = true;
            lib.type = ele.type;
            lib.nodeType = "lib";

            if(ele.shelves){
                 var children = new Array();
                 $.each(ele.shelves,function(index,ele){
                    var shelf = new Object();
                    shelf.name = ele.name;
                    shelf.id = ele.id;
                    shelf.type = ele.type;
                    shelf.nodeType = "shelf";

                    children.push(shelf);
                 });
                 lib.children = children;
            }
            treeData.push(lib);
        });
        root.children = treeData;
        //渲染成树
        var libTree = $.fn.zTree.init($(libTreeId), TreeSettings, root);
        libTree.shelfTreeId = shelfTreeId;
      });
    },
    loadShelfTree : function(type,libTreeId,shelfTreeId){
      $.get("/shelf/tree",{"use":type},function(data,status){
        var root = new Object();
        root.isParent = true;
//        root.chkDisabled = true;
        root.open = true;
        root.name = "书架列表";

        var treeData = new Array();
        $.each(data,function(index,ele){
            var shelf = new Object();
            shelf.name = ele.name;
            shelf.id = ele.id;
            shelf.type = ele.type;
            shelf.nodeType = "shelf";
            treeData.push(shelf);
        });

        root.children = treeData;
        //渲染成树
        var shelfTree = $.fn.zTree.init($(shelfTreeId), TreeSettings, root);
        shelfTree.libTreeId = libTreeId;
      });
    }
}