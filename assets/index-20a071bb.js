(function(){const t=document.createElement("link").relList;if(t&&t.supports&&t.supports("modulepreload"))return;for(const r of document.querySelectorAll('link[rel="modulepreload"]'))o(r);new MutationObserver(r=>{for(const i of r)if(i.type==="childList")for(const u of i.addedNodes)u.tagName==="LINK"&&u.rel==="modulepreload"&&o(u)}).observe(document,{childList:!0,subtree:!0});function n(r){const i={};return r.integrity&&(i.integrity=r.integrity),r.referrerpolicy&&(i.referrerPolicy=r.referrerpolicy),r.crossorigin==="use-credentials"?i.credentials="include":r.crossorigin==="anonymous"?i.credentials="omit":i.credentials="same-origin",i}function o(r){if(r.ep)return;r.ep=!0;const i=n(r);fetch(r.href,i)}})();function l(){}function I(e){return e()}function k(){return Object.create(null)}function y(e){e.forEach(I)}function T(e){return typeof e=="function"}function M(e,t){return e!=e?t==t:e!==t||e&&typeof e=="object"||typeof e=="function"}function Q(e){return Object.keys(e).length===0}function U(e,...t){if(e==null)return l;const n=e.subscribe(...t);return n.unsubscribe?()=>n.unsubscribe():n}function V(e,t,n){e.$$.on_destroy.push(U(t,n))}function W(e,t,n){return e.set(n),t}function A(e,t){e.appendChild(t)}function N(e,t,n){e.insertBefore(t,n||null)}function x(e){e.parentNode&&e.parentNode.removeChild(e)}function X(e,t){for(let n=0;n<e.length;n+=1)e[n]&&e[n].d(t)}function _(e){return document.createElement(e)}function Y(e){return document.createTextNode(e)}function Z(){return Y(" ")}function v(e,t,n){n==null?e.removeAttribute(t):e.getAttribute(t)!==n&&e.setAttribute(t,n)}function ee(e){return Array.from(e.childNodes)}let p;function m(e){p=e}function te(){if(!p)throw new Error("Function called outside component initialization");return p}function ne(e){te().$$.on_mount.push(e)}const h=[],P=[],$=[],q=[],re=Promise.resolve();let E=!1;function oe(){E||(E=!0,re.then(D))}function O(e){$.push(e)}const w=new Set;let g=0;function D(){const e=p;do{for(;g<h.length;){const t=h[g];g++,m(t),ie(t.$$)}for(m(null),h.length=0,g=0;P.length;)P.pop()();for(let t=0;t<$.length;t+=1){const n=$[t];w.has(n)||(w.add(n),n())}$.length=0}while(h.length);for(;q.length;)q.pop()();E=!1,w.clear(),m(e)}function ie(e){if(e.fragment!==null){e.update(),y(e.before_update);const t=e.dirty;e.dirty=[-1],e.fragment&&e.fragment.p(e.ctx,t),e.after_update.forEach(O)}}const b=new Set;let ce;function K(e,t){e&&e.i&&(b.delete(e),e.i(t))}function se(e,t,n,o){if(e&&e.o){if(b.has(e))return;b.add(e),ce.c.push(()=>{b.delete(e),o&&(n&&e.d(1),o())}),e.o(t)}else o&&o()}function ue(e){e&&e.c()}function R(e,t,n,o){const{fragment:r,after_update:i}=e.$$;r&&r.m(t,n),o||O(()=>{const u=e.$$.on_mount.map(I).filter(T);e.$$.on_destroy?e.$$.on_destroy.push(...u):y(u),e.$$.on_mount=[]}),i.forEach(O)}function G(e,t){const n=e.$$;n.fragment!==null&&(y(n.on_destroy),n.fragment&&n.fragment.d(t),n.on_destroy=n.fragment=null,n.ctx=[])}function le(e,t){e.$$.dirty[0]===-1&&(h.push(e),oe(),e.$$.dirty.fill(0)),e.$$.dirty[t/31|0]|=1<<t%31}function H(e,t,n,o,r,i,u,c=[-1]){const f=p;m(e);const s=e.$$={fragment:null,ctx:[],props:i,update:l,not_equal:r,bound:k(),on_mount:[],on_destroy:[],on_disconnect:[],before_update:[],after_update:[],context:new Map(t.context||(f?f.$$.context:[])),callbacks:k(),dirty:c,skip_bound:!1,root:t.target||f.$$.root};u&&u(s.root);let j=!1;if(s.ctx=n?n(e,t.props||{},(a,C,...L)=>{const S=L.length?L[0]:C;return s.ctx&&r(s.ctx[a],s.ctx[a]=S)&&(!s.skip_bound&&s.bound[a]&&s.bound[a](S),j&&le(e,a)),C}):[],s.update(),j=!0,y(s.before_update),s.fragment=o?o(s.ctx):!1,t.target){if(t.hydrate){const a=ee(t.target);s.fragment&&s.fragment.l(a),a.forEach(x)}else s.fragment&&s.fragment.c();t.intro&&K(e.$$.fragment),R(e,t.target,t.anchor,t.customElement),D()}m(f)}class J{$destroy(){G(this,1),this.$destroy=l}$on(t,n){if(!T(n))return l;const o=this.$$.callbacks[t]||(this.$$.callbacks[t]=[]);return o.push(n),()=>{const r=o.indexOf(n);r!==-1&&o.splice(r,1)}}$set(t){this.$$set&&!Q(t)&&(this.$$.skip_bound=!0,this.$$set(t),this.$$.skip_bound=!1)}}const d=[];function fe(e,t=l){let n;const o=new Set;function r(c){if(M(e,c)&&(e=c,n)){const f=!d.length;for(const s of o)s[1](),d.push(s,e);if(f){for(let s=0;s<d.length;s+=2)d[s][0](d[s+1]);d.length=0}}}function i(c){r(c(e))}function u(c,f=l){const s=[c,f];return o.add(s),o.size===1&&(n=t(r)||l),c(e),()=>{o.delete(s),o.size===0&&(n(),n=null)}}return{set:r,update:i,subscribe:u}}let z=fe([]);function ae(){let e;return fetch("https://github.com/TomerPacific/MediumArticles/blob/master/README.md",{headers:{"Access-Control-Allow-Origin":"https://tomerpacific.github.io"}}).then(function(t){return t.text()}).then(function(t){return console.log(t),e}).catch(function(t){return console.error(t),[]}),[]}function B(e,t,n){const o=e.slice();return o[1]=t[n],o}function F(e){let t;return{c(){t=_("div"),v(t,"class","grid-item")},m(n,o){N(n,t,o)},p:l,d(n){n&&x(t)}}}function de(e){let t,n,o=e[0],r=[];for(let i=0;i<o.length;i+=1)r[i]=F(B(e,o,i));return{c(){t=_("main"),n=_("div");for(let i=0;i<r.length;i+=1)r[i].c();v(n,"id","grid-container"),v(n,"class","svelte-cwvvuj")},m(i,u){N(i,t,u),A(t,n);for(let c=0;c<r.length;c+=1)r[c].m(n,null)},p(i,[u]){if(u&1){o=i[0];let c;for(c=0;c<o.length;c+=1){const f=B(i,o,c);r[c]?r[c].p(f,u):(r[c]=F(),r[c].c(),r[c].m(n,null))}for(;c<r.length;c+=1)r[c].d(1);r.length=o.length}},i:l,o:l,d(i){i&&x(t),X(r,i)}}}function he(e,t,n){let o;return V(e,z,r=>n(0,o=r)),ne(()=>{W(z,o=ae(),o)}),[o]}class me extends J{constructor(t){super(),H(this,t,he,de,M,{})}}function _e(e){let t,n,o,r,i;return r=new me({}),{c(){t=_("main"),n=_("h2"),n.textContent="Medium Articles",o=Z(),ue(r.$$.fragment)},m(u,c){N(u,t,c),A(t,n),A(t,o),R(r,t,null),i=!0},p:l,i(u){i||(K(r.$$.fragment,u),i=!0)},o(u){se(r.$$.fragment,u),i=!1},d(u){u&&x(t),G(r)}}}class pe extends J{constructor(t){super(),H(this,t,null,_e,M,{})}}new pe({target:document.getElementById("app")});
