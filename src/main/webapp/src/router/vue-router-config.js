//解决路由跳转原路由或者刷新出错
export function JumpOrRefreshError(VueRouter) {
  // replace：跳转后无法返回
  const originalReplace = VueRouter.prototype.replace;
  VueRouter.prototype.replace = function replace(location) {
    return originalReplace.call(this, location).catch(err => err);
  };

  // push：跳转后可以返回
  const originalPush = VueRouter.prototype.push;
  VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err);
  }
}

