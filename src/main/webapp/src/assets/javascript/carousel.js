import classObj from './utils/className.js';

function carouselLoad() {

  let carouselItems = document.querySelector(".carousel-box");
  let leftBtn = document.querySelector("#left");
  let rightBtn = document.querySelector("#right");
  let pointItems = document.querySelector(".point-items");

  // 定位函数
  function refreshLocation(index) {
    let len = carouselItems.children.length;
    let left = index;
    let center = left + 1;
    let right = center + 1;

    // 限制
    if(index == len-1) {
      center = 0;
      right = center + 1;
    }
    if(index == len-1-1) {
      right = 0;
    }

    classObj.replaceClass(pointItems.children[left], 'active-point');

    classObj.replaceClass(carouselItems.children[left], 'carousel-left');
    classObj.replaceClass(carouselItems.children[center], 'carousel-center');
    classObj.replaceClass(carouselItems.children[right], 'carousel-right');

    for(let i = 0; i < len; i++){
      if(i != left && i != center && i != right)
        classObj.replaceClass(carouselItems.children[i], 'carousel-others');

      if(i != left)
        classObj.removeClass(pointItems.children[i], 'active-point');
    }
  }

  // 初始化位置
  refreshLocation(0);
  let index = 0;
  // 上一个
  function toPre() {
    index--;
    if(index < 0){
      index = carouselItems.children.length-1;
    }
    refreshLocation(index);
  }

  leftBtn.addEventListener('click', function() {
    toPre();
    clearInterval(timer);
  });

  // 下一个
  function toNext() {
    index++;
    if(index >= carouselItems.children.length){
      index = 0;
    }
    refreshLocation(index);
  }

  rightBtn.addEventListener('click', function() {
    toNext();
    clearInterval(timer);
  });

  // —————————————— 点击小圆点 ——————————————
  pointItems.onclick = function(e) {
    if(e.target != pointItems) {
      let num = 0;    // 当前点击的小圆点
      while(num != 5) {
        if(pointItems.children[num] == e.target)
          break;
        else
          num++;
      }

      // 根据点击的小圆点，跳转到指定的轮播图
      index = num;
      refreshLocation(index);
    }
  }

  // —————————————— 自动轮播 ——————————————
  let timer = setInterval(toNext, 3000);
  let insertBox = document.querySelector(".insert-box");
  leftBtn.style.display = 'none';
  rightBtn.style.display = 'none';

  // 鼠标移入，停止轮播
  insertBox.onmouseover = function() {
    leftBtn.style.display = 'block';
    rightBtn.style.display = 'block';
    clearInterval(timer);
  }

  // 鼠标移出，接着轮播
  insertBox.onmouseout = function() {
    leftBtn.style.display = 'none';
    rightBtn.style.display = 'none';
    timer = setInterval(toNext, 3000);
  }

  // 防止抽风-失去焦点与否（时有时无系列）
  // window.onfcous = function() {
  //   timer = setInterval(toNext, 3000);
  // }

  // window.onblus = function() {
  //   clearInterval(timer);
  // }
}

export default {
  carouselLoad
}
