import classObj from './className';

function slowToTop()
{
  let time = setInterval(function(){
    let fromTop = document.body.scrollTop || document.documentElement.scrollTop;  // 当前位置
    let speed = Math.floor(-fromTop/5);   // 滑动速度
    if(fromTop == 0){   // 滑动顶部
      clearInterval(time);
      return;
    }
    fromTop = document.body.scrollTop = document.documentElement.scrollTop = fromTop + speed;
  },30);
}

// 点击后缓慢到达所在位置：target 跳转目标地
function slowToTarget(target) {
  if(target) {
    let time = setInterval(function(){
      let fromTop = document.body.scrollTop || document.documentElement.scrollTop;  // 当前位置

      if(Math.abs(fromTop - target.offsetTop) < 20){   // 滑动顶部
        window.scrollTo(0, target.offsetTop);
        clearInterval(time);
        return;
      }

      if (fromTop - target.offsetTop > 20) {
        fromTop -= 20;
        window.scrollTo(0, fromTop);
      } else {
        fromTop += 20;
        window.scrollTo(0, fromTop);
      }
    },10);
  }
}

// 判断当前对应的序列号，num为当前序号
function takeIndex(click, len) {
  let num = 0;      // 获取点击的序列号
  while(num != len) {
    if(click == click.parentNode.children[num]){
      break;
    } else {
      num++;
    }
  }
  return num;
}

// 判断是哪个按钮，对应的对象是谁
function judgmentBtn(btn) {
  let target = null;  // 点击的按钮的目的对象
  let num = takeIndex(btn, 3);      // 获取点击的序列号
  // while(num != 3) {
  //   if(btn == btn.parentNode.children[num]){
  //     break;
  //   } else {
  //     num++;
  //   }
  // }
  // 对应的位置-对应顺序
  if(num == 0) {
    target = document.querySelector("#homepage-Learning-World");
  } else if(num == 1) {
    target = document.querySelector("#homePage_Professional_Introduction");
  } else if(num == 2) {
    target = document.querySelector("#homePage_University_Life");
  } else {
    return;
  }
    return target;
}

// 在指定区域内的样式
function scrollStype(btn, target) {
  // current_position 当前位置，container_height 容器高度
  let current_position = (document.documentElement.scrollTop || document.body.scrollTop);
  let container_height = (target == document.querySelector("#homePage_University_Life"))? target.offsetHeight + 250 : target.offsetHeight;

  if((target.offsetTop - container_height/2) < current_position && current_position < (target.offsetTop + container_height/2)) {
    classObj.replaceClass(btn, 'active');
  }else {
    classObj.removeClass(btn, 'active');
  }
}

// 侧栏的伸缩
function sidebarStatus(sidebar) {
  let current_position = (document.documentElement.scrollTop || document.body.scrollTop);
  let HP_textPart = document.querySelector('#HP_textPart');
  if(current_position > HP_textPart.offsetTop-200){
    classObj.addClass(sidebar, 'homePage_appear');
  } else {
    classObj.removeClass(sidebar, 'homePage_appear');
  }
}

// window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop

export default {
  slowToTop,
  slowToTarget,
  judgmentBtn,
  scrollStype,
  sidebarStatus,
  takeIndex
}
