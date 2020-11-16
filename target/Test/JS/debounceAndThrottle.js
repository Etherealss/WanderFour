//防抖函数
function debounce(func,wait,immediate)
{
    let timeout,result;
    let decounced = function()
    {   
        let context = this; //改变执行函数内部this的指向
        let args = arguments;   //event指向问题
        
        if(timeout)
        {
            clearTimeout(timeout);
        }
        
        if(immediate)   
        {
            let callNow = !timeout; //一开始timeout不存在为false，取反为true
            timeout = setTimeout(function(){
                timeout = null;
            }, wait);
            //这部分会立即执行
            if(callNow)
            {
                result = func.apply(context,args);
            }
        }
        else{
            //这部分不会立即执行
            timeout = setTimeout(function(){
                func.apply(context,args);  
            },wait);
        }
        return result;
    }

    //防抖函数的取消操作
    decounced.cancel = function(){
        clearTimeout(timeout);
        timeout = null;
    }
    
    return decounced;
}

//节流函数（时间戳+定时器，可控制首次是否立即执行，以及最后一次是否执行）
function throttle(func,wait,options)
{
    let context,args,timeout,result;
    let previous = 0;    //之前的时间戳
    if(!options) 
        options = {};
    
    let later = function()
    {
        previous = new Date().valueOf();    //更新时间戳
        timeout = null;    //置空，让下一次能够再执行进来
        func.apply(context,args);
    }

    let throttled = function()
    {
        context = this; //改变执行函数内部this的指向
        args = arguments;   //event指向问题
        let now = new Date().valueOf();

        if(options.leading == false && !previous)
        {
            previous = now;
        }
        if(now - previous > wait)   
        {
            //第一次直接执行
            if(timeout) //如果有值，清除掉
            {
                clearTimeout(timeout);
                timeout = null;
            }

            func.apply(context,args);
            previous = now;
        }
        else if(!timeout && options.trailing != false) //undefined的取反为有值
        {
            //最后一次会被执行
            timeout = setTimeout(later, wait);
        }
        return result;
    }

    //节流的取消操作
    throttled.cancel = function()
    {
        clearTimeout(timeout);
        previous = 0;
        timeout = context = args = null;
    }

    return throttled;
}