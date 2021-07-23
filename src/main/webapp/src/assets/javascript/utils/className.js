export default {
  isHasClass,
  addClass,
  removeClass,
  toggleClass,
  replaceClass
}

// 判断样式是否存在
// elm: element, clsn: className
function isHasClass(elm, clsn) {
  let reg = new RegExp("(\\s|^)" + clsn +"(\\s|$)");
  return elm.className.match(reg);
}

// 为指定元素添加样式
function addClass(elm, clsn) {
  if(!isHasClass(elm, clsn)) {
    elm.className += " " + clsn;
  }
}

// 为指定元素删除样式
function removeClass(elm, clsn) {
  if(isHasClass(elm, clsn)) {
    let reg = new RegExp("(\\s|^)"+ clsn +"(\\s|$)");
    elm.className = elm.className.replace(reg, " ").replace(/(^\s*)|(\s*$)/g, "");;
    // str.replace(/(^\s*)|(\s*$)/g, ""); 去掉首尾空格
  }
}

// 和 jQuery 的 toggleClass 同样的效果
function toggleClass(elm, clsn) {
  if(isHasClass(elm, clsn)) {
    removeClass(elm, clsn);
  } else {
    addClass(elm, clsn);
  }
}

// 替换Class：先清空再替换
function replaceClass(elm, clsn) {
  if(!isHasClass(elm, clsn)){
    elm.className = " ".replace(/(^\s*)|(\s*$)/g, "");
    addClass(elm, clsn);
  }
}


//  let HLW_position = document.querySelector("#homepage-Learning-World");
//   let HPI_position = document.querySelector("#homePage_Professional_Introduction");
//   let HUL_position = document.querySelector("#homePage_University_Life");
