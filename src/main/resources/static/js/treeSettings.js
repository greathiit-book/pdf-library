TreeSettings = {
    libTree : {
       check:{
           enable:true,
           chkDisabledInherit:false
       },
       edit:{
           enable:true,
           editNameSelectAll:true,
           removeTitle:"删除",
           renameTitle:"重命名"
       },
       callback:{
           beforeRemove : LibraryOperation.beforeDelete,
           beforeRename : LibraryOperation.beforeRename
       }
    }
}