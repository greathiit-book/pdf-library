TreeLoader = {
    loadLibTree : function(type,treeEle){
      $.get("/library/tree",{"use":type},function(data,status){
        var treeData = new Array();
        $.each(data,function(index,ele){
            var lib = new Object();
            lib.isParent = true;
            lib.name = ele.name;
            lib.id = ele.id;
            lib.type = ele.type;

            if(ele.shelves){
                 var children = new Array();
                 $.each(ele.shelves,function(index,ele){
                    var shelf = new Object();
                    shelf.name = ele.name;
                    shelf.id = ele.id;
                    shelf.type = ele.type;
                    children.push(shelf);
                 });
                 lib.children = children;
            }
            treeData.push(lib);
        });

        //渲染成树
        var treeId = $.fn.zTree.init(treeEle, TreeSettings, treeData);
        return treeId;
      });
    }
}