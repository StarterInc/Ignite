(window.webpackJsonp=window.webpackJsonp||[]).push([[4],{142:function(e,t,a){"use strict";a.r(t);var n=a(10),r=a.n(n),i=a(165),o=a.n(i),c=a(0),l=a.n(c),s=a(157),m=a(153),u=(a(205),a(6)),d=a.n(u),p=a(146),h=a(167),f=a.n(h),g=a(174),v=(a(214),a(215)),b=function(e,t,a){return void 0!==a&&a.length>0?a:e.endsWith("success.html")?"Final Step":"Step "+t},E=function(e,t){return t===e?"active":t<e?"":t>=e?"complete":void 0},N=function(e){return l.a.createElement(p.StaticQuery,{query:"3819603454",render:function(t){var a=t.allMdx.edges.map(function(e){return e.node.fields}),n=e.stepNumber;return l.a.createElement(l.a.Fragment,null,l.a.createElement("div",{className:"container-fluid"},l.a.createElement("div",{className:"sheet"},l.a.createElement("ol",{className:"multi-step-nav multi-step-nav-collapse-sm multi-step-indicator-label-top"},a.map(function(e,t){var r=e.slug,i=e.stepNumber,o=e.short;return l.a.createElement("li",{key:t,className:E(i,n)+" multi-step-item multi-step-item-expand"},a[t+1]&&l.a.createElement("div",{className:"multi-step-divider"}),l.a.createElement("div",{className:"multi-step-indicator"},l.a.createElement("div",{className:"multi-step-indicator-label"},b(r,i,o)),l.a.createElement(p.Link,{className:"multi-step-icon","data-multi-step-icon":""+i,to:r})))})))))},data:v})},y=a(164),w=function(e){function t(){return e.apply(this,arguments)||this}return d()(t,e),t.prototype.render=function(){return l.a.createElement("div",{className:"onboarding-main"},l.a.createElement(g.a,{components:{h1:y.a.H1,h2:y.a.H2,h3:y.a.H3,h4:y.a.H4,p:y.a.P}},this.props.codeBody),l.a.createElement("div",{className:"social"},l.a.createElement("div",{className:"social-buttons"},l.a.createElement("div",{className:"btn-group"},l.a.createElement("div",{className:"btn-group-item"},l.a.createElement(p.Link,{to:"onboarding/one.html",className:"btn btn-primary"},"Get Started"))))))},t}(c.Component),S=a(156),_=a(162),x=a(163),k=a(151),C=a(155),T=function(e){function t(){return e.apply(this,arguments)||this}d()(t,e);var a=t.prototype;return a.componentDidMount=function(){this._codeTabs=new _.a,this._codeClipboard=new x.a},a.componentWillUnmount=function(){this._codeTabs=null,this._codeClipboard.dispose()},a.render=function(){var e=this.props.data.mdx,t=e.code.body,a=e.excerpt,n=e.timeToRead,r=e.frontmatter,i=r.mainPage,o=r.title,c=r.stepNumber,s=r.description,m=r.needsAuth,u=this.props.pageContext,d=u.previous,h=u.next;return l.a.createElement(C.a,{needsAuth:m},l.a.createElement("div",{className:"onboarding"},l.a.createElement(f.a,null,l.a.createElement("title",null,o),l.a.createElement("meta",{name:"description",content:a}),l.a.createElement("meta",{name:"og:description",content:a}),l.a.createElement("meta",{name:"twitter:description",content:a}),l.a.createElement("meta",{name:"og:title",content:o}),l.a.createElement("meta",{name:"og:type",content:"article"}),l.a.createElement("meta",{name:"twitter.label1",content:"Reading time"}),l.a.createElement("meta",{name:"twitter:data1",content:n+" min read"})),l.a.createElement("header",{className:"header"},l.a.createElement(k.a,{opaque:!i,fixed:i}),i&&l.a.createElement("div",{className:"container-fluid"},l.a.createElement("div",{className:"row"},l.a.createElement("div",{className:"intro blog-intro text-center col"},l.a.createElement("div",{className:"container-fluid container-fluid-max-lg"},l.a.createElement("h1",{className:"h1"},o),l.a.createElement("h2",{className:"h3"},s)))))),l.a.createElement("main",{className:"content"},l.a.createElement("div",{className:"clay-site-container container"},l.a.createElement("div",{className:"row"},l.a.createElement("div",{className:"col-md-12"},!i&&l.a.createElement(l.a.Fragment,null,l.a.createElement(N,{stepNumber:c}),l.a.createElement("div",{className:"article-header"},l.a.createElement("h1",{className:"clay-h1"},o),l.a.createElement("cite",{className:"clay-h4"},s)),l.a.createElement("article",null,l.a.createElement(g.a,{components:{h1:y.a.H1,h2:y.a.H2,h3:y.a.H3,h4:y.a.H4,p:y.a.P}},t),l.a.createElement("div",{className:"social"},l.a.createElement("div",{className:"social-buttons"},l.a.createElement("div",{className:"btn-group"},d.fields&&l.a.createElement("div",{className:"btn-group-item"},l.a.createElement(p.Link,{to:d.fields.slug,className:"btn btn-secondary"},"Previous")),h.fields&&h.fields.slug.startsWith("onboarding")&&l.a.createElement("div",{className:"btn-group-item"},l.a.createElement(p.Link,{to:h.fields.slug,className:"btn btn-primary"},"Next Step"))))))),i&&l.a.createElement(w,{codeBody:t}))))),l.a.createElement(S.a,null)))},t}(c.Component);a.d(t,"pageQuery",function(){return L});var L="2860948260";t.default=function(e){var t=e.children,a=o()(e,["children"]);return l.a.createElement(s.MDXScopeProvider,{__mdxScope:r()({},m.a)},l.a.createElement(T,a,t))}},146:function(e,t,a){"use strict";a.r(t),a.d(t,"graphql",function(){return h}),a.d(t,"StaticQueryContext",function(){return d}),a.d(t,"StaticQuery",function(){return p});var n=a(0),r=a.n(n),i=a(4),o=a.n(i),c=a(144),l=a.n(c);a.d(t,"Link",function(){return l.a}),a.d(t,"withPrefix",function(){return c.withPrefix}),a.d(t,"navigate",function(){return c.navigate}),a.d(t,"push",function(){return c.push}),a.d(t,"replace",function(){return c.replace}),a.d(t,"navigateTo",function(){return c.navigateTo});var s=a(148),m=a.n(s);a.d(t,"PageRenderer",function(){return m.a});var u=a(29);a.d(t,"parsePath",function(){return u.a});var d=r.a.createContext({}),p=function(e){return r.a.createElement(d.Consumer,null,function(t){return e.data||t[e.query]&&t[e.query].data?(e.render||e.children)(e.data?e.data.data:t[e.query].data):r.a.createElement("div",null,"Loading (StaticQuery)")})};function h(){throw new Error("It appears like Gatsby is misconfigured. Gatsby related `graphql` calls are supposed to only be evaluated at compile time, and then compiled away,. Unfortunately, something went wrong and the query was left in the compiled code.\n\n.Unless your site has a complex or custom babel/Gatsby configuration this is likely a bug in Gatsby.")}p.propTypes={data:o.a.object,query:o.a.string.isRequired,render:o.a.func,children:o.a.func}},147:function(e,t,a){"use strict";a.d(t,"a",function(){return o}),a.d(t,"b",function(){return c}),a.d(t,"c",function(){return l}),a.d(t,"d",function(){return s});a(30),a(172);var n=a(160),r=function(e){return window.localStorage.setItem("gatsbyUser",JSON.stringify(e))},i=n.Nothing,o=function(e){var t=e.email,a=e.password;if(!l())return new Promise(function(e,n){i.signInWithEmailAndPassword(t,a).then(function(t){var a=t.data_,n=a.createdAt,i=a.email,o=a.id,c=a.token;r({createdAt:n,email:i,id:o,token:c}),e()}).catch(function(e){n(alert(e.error_description))})})},c=function(e){var t=e.email,a=e.password;if(!l())return new Promise(function(e,n){i.createUser({email:t,password:a}).then(function(t){e()}).catch(function(e){n(alert(e))})})},l=function(){return!!i.currentUser},s=function(){return r({}),i.signOut()}},148:function(e,t,a){var n;e.exports=(n=a(150))&&n.default||n},150:function(e,t,a){"use strict";a.r(t);var n=a(10),r=a.n(n),i=a(0),o=a.n(i),c=a(4),l=a.n(c),s=a(49),m=a(2),u=function(e){var t=e.location,a=m.default.getResourcesForPathnameSync(t.pathname);return o.a.createElement(s.a,r()({location:t,pageResources:a},a.json))};u.propTypes={location:l.a.shape({pathname:l.a.string.isRequired}).isRequired},t.default=u},151:function(e,t,a){"use strict";a(170);var n=a(6),r=a.n(n),i=a(48),o=a.n(i),c=a(171),l=a(0),s=a.n(l),m=a(146),u=a(161),d=a.n(u),p=a(147),h=function(e){function t(){var t;return(t=e.call(this)||this).expandToggler=function(){t.props.onNavbarToggleClick()},t._rootNode=c.window||c.document,t._addScroll=t._addScroll.bind(o()(o()(t))),t}r()(t,e);var a=t.prototype;return a._getScrollTop=function(){return this._rootNode===c.window?this._rootNode.pageYOffset:this._rootNode===c.document?this._rootNode.defaultView.pageYOffset:void 0},a._addScroll=function(){this._getScrollTop()>=50?this.refs.navElement.classList.add("scroll"):this.refs.navElement.classList.remove("scroll")},a._handleLogout=function(){var e=this;Object(p.d)().then(function(){e.forceUpdate()}).catch(function(e){alert(e),c.window.location.reload()})},a.componentDidMount=function(){this.props.static||this._rootNode.addEventListener("scroll",this._addScroll,!1)},a.componentWillUnmount=function(){this.props.static||this._rootNode.removeEventListener("scroll",this._addScroll,!1)},a.render=function(){var e=this.props,t=e.fixed,a=void 0===t||t,n=e.opaque,r=void 0!==n&&n,i=e.effect,o=void 0!==i&&i,c=e.sidebarHamburguerIcon,l=void 0!==c&&c,u=d()("navbar navbar-clay-site navbar-expand-lg navbar-dark",{"fixed-top":a,scroll:o,"bg-primary":r});return s.a.createElement("nav",{ref:"navElement",className:u},s.a.createElement("div",{className:"container-fluid"},s.a.createElement(m.Link,{to:"/",className:"navbar-brand"},s.a.createElement("img",{className:"logo mr-2",alt:"Starter StackGen Logo",src:"/logos/StackGenLogo-2019.png"})),l&&s.a.createElement("button",{onClick:this.expandToggler,className:"navbar-toggler p-2 order-md-1",type:"button","data-toggle":"collapse","data-target":"#claySidebar","aria-controls":"claySidebar","aria-expanded":"false","aria-label":"Toggle navigation"},s.a.createElement("svg",{"aria-hidden":"true",className:"lexicon-icon lexicon-icon-bars"},s.a.createElement("use",{xlinkHref:"/images/icons/icons.svg#bars"}))),s.a.createElement("ul",{className:"navbar-nav ml-md-auto"},s.a.createElement("li",{className:"nav-item"},s.a.createElement(m.Link,{className:"nav-link ml-lg-3",to:"/docs/"},"Docs")),s.a.createElement("li",{className:"nav-item"},s.a.createElement("a",{className:"nav-link ml-lg-3",href:"http://stackgen.io/"},"Blog")),s.a.createElement("li",{className:"nav-item"},s.a.createElement(m.Link,{className:"nav-link ml-lg-3",to:"/updates/"},"Updates")),s.a.createElement("li",{className:"nav-item"},s.a.createElement("a",{className:"mx-3 mr-lg-0",href:"https://github.com/StarterInc/StackGen",target:"_blank",rel:"noopener noreferrer"},s.a.createElement("img",{src:"/images/home/GitHub-Mark-64px.svg",alt:""}))))))},t}(l.Component);t.a=h},153:function(e,t,a){"use strict";var n=a(0),r=a.n(n),i=a(166);t.a={React:r.a,MDXTag:i.MDXTag}},155:function(e,t,a){"use strict";var n=a(6),r=a.n(n),i=a(0),o=a.n(i),c=a(147),l=(a(173),function(e){function t(){for(var t,a=arguments.length,n=new Array(a),r=0;r<a;r++)n[r]=arguments[r];return(t=e.call.apply(e,[this].concat(n))||this).state={email:"",password:""},t.handleUpdate=function(e){var a;t.setState(((a={})[e.target.name]=e.target.value,a))},t._handleSignUp=function(e){e.preventDefault(),Object(c.b)(t.state).then(function(){t.props.changeLoginState(!0)}).catch(function(){t.props.changeLoginState(!1)})},t._handleSubmit=function(e){e.preventDefault(),Object(c.a)(t.state).then(function(){t.props.changeLoginState(!0)}).catch(function(){t.props.changeLoginState(!1)})},t}return r()(t,e),t.prototype.render=function(){var e=this;return o.a.createElement(o.a.Fragment,null,o.a.createElement("div",{className:"login"},o.a.createElement("div",{className:"clay-site-container container-fluid"},o.a.createElement("div",{className:"row"},o.a.createElement("div",{className:"col-md-12"},o.a.createElement("div",{className:"sheet"},o.a.createElement("div",{className:"sheet-header"},o.a.createElement("h2",{className:"sheet-title"},"Login")),o.a.createElement("form",{method:"post",onSubmit:function(t){e._handleSubmit(t)}},o.a.createElement("div",{className:"form-group"},o.a.createElement("label",{htmlFor:"basicInputTypeEmail"},"Email"),o.a.createElement("input",{className:"form-control",autoComplete:"email",type:"email",name:"email",onChange:this.handleUpdate,id:"basicInputTypeEmail"})),o.a.createElement("div",{className:"form-group"},o.a.createElement("label",{htmlFor:"basicInputTypePassword"},"Password"),o.a.createElement("input",{className:"form-control",autoComplete:"current-password",id:"basicInputTypePassword",onChange:this.handleUpdate,placeholder:"Enter password",name:"password",type:"password"})),o.a.createElement("div",{className:"form-group"},o.a.createElement("div",{className:"btn-group"},o.a.createElement("div",{className:"btn-group-item"},o.a.createElement("button",{className:"btn btn-primary",type:"submit"},"Submit")),o.a.createElement("div",{className:"btn-group-item"},o.a.createElement("a",{className:"btn btn-secondary",onClick:function(t){e._handleSignUp(t)},href:"#no"},"Sign Up")))))))))))},t}(i.Component)),s=function(e){function t(t){var a;return(a=e.call(this,t)||this).state={login:!1},a}r()(t,e);var a=t.prototype;return a.changeLoginState=function(e){this.setState(function(){return{login:e}})},a.componentDidMount=function(){this.setState({login:!!Object(c.c)()})},a.render=function(){return this.props.needsAuth&&!this.state.login?o.a.createElement(l,{changeLoginState:this.changeLoginState.bind(this)}):this.props.children},t}(i.Component);t.a=s},156:function(e,t,a){"use strict";var n=a(0),r=a.n(n),i=function(){return r.a.createElement("div",{className:"footer"},r.a.createElement("div",{className:"container-fluid container-fluid-max-lg"},r.a.createElement("div",{className:"row"},r.a.createElement("div",{className:"col-lg text-center text-sm-left mb-4 mb-lg-0"}),r.a.createElement("div",{className:"col-lg text-center text-sm-right"},r.a.createElement("div",null,r.a.createElement("br",null),"Powered by ",r.a.createElement("a",{href:"http://docs.stackgen.io/",target:"_blank",rel:"noopener noreferrer"},r.a.createElement("span",{className:"title align-middle"},"StackGen"))),"Built with ❤️ in San Francisco by ",r.a.createElement("a",{href:"http://www.starter.io",className:"font-weight-bold",target:"_blank",rel:"noopener noreferrer"},r.a.createElement("img",{className:"logo",alt:"starter logo",src:"/logos/starter_logo_vertical_color@x2.png"}))))))};t.a=i},162:function(e,t,a){"use strict";a(70);var n=a(149),r=a.n(n),i=a(179),o=a.n(i),c=function(){function e(){var e=this,t=[];Array.prototype.slice.call(document.querySelectorAll(".code-container")).forEach(function(a){t.push({label:e._getTabLabelFromElement(a),element:a}),a.nextElementSibling&&r.a.hasClass(a.nextElementSibling,"code-container")||(t.length>1&&e._renderTabs(t),t=[])})}var t=e.prototype;return t._getTabLabelFromElement=function(e){return e.getAttribute("data-label")},t._hide=function(e){r.a.addClasses(e,"hide")},t._hideAll=function(e){var t=this;e.forEach(function(e){t._hide(e.element)})},t._renderTabs=function(e){var t=this,a=r.a.buildFragment('<div class="tabContainer"></div>'),n=new o.a({elementClasses:"nav-code-tabs",tabs:e},a);n.on("changeRequest",function(e){var a=e.state.tab;t._hideAll(n.tabs),t._show(n.tabs[a].element)}),this._hideAll(n.tabs),this._show(n.tabs[0].element),e[0].element.parentNode.insertBefore(a,e[0].element)},t._show=function(e){r.a.removeClasses(e,"hide")},e}();t.a=c},163:function(e,t,a){"use strict";var n=a(181),r=a.n(n),i=a(182),o=a.n(i),c=function(){},l=function(){function e(){this.clayTooltip=new r.a(new c),this.clayClipboard=new o.a({selector:".code-container .btn-copy",text:function(e){return e.setAttribute("title","Copied"),setTimeout(function(){e.setAttribute("title","Copy")},2e3),e.parentNode.querySelector("pre code").innerText}})}return e.prototype.dispose=function(){this.clayTooltip.dispose(),this.clayClipboard.dispose()},e}();t.a=l},164:function(e,t,a){"use strict";var n={};a.r(n),a.d(n,"H1",function(){return o}),a.d(n,"H2",function(){return c}),a.d(n,"H3",function(){return l}),a.d(n,"H4",function(){return s}),a.d(n,"P",function(){return m});var r=a(0),i=a.n(r),o=function(e){return i.a.createElement("h1",{className:"clay-h1"},e.children)},c=function(e){return i.a.createElement("h2",{className:"clay-h2"},e.children)},l=function(e){return i.a.createElement("h3",{className:"clay-h3"},e.children)},s=function(e){return i.a.createElement("h4",{className:"clay-h4"},e.children)},m=function(e){return i.a.createElement("p",{className:"clay-p"},e.children)};t.a=n},205:function(e,t,a){"use strict";var n=a(14),r=a(31),i=a(71),o="".startsWith;n(n.P+n.F*a(72)("startsWith"),"String",{startsWith:function(e){var t=i(this,e,"startsWith"),a=r(Math.min(arguments.length>1?arguments[1]:void 0,t.length)),n=String(e);return o?o.call(t,n,a):t.slice(a,a+n.length)===n}})},214:function(e,t,a){"use strict";var n=a(14),r=a(31),i=a(71),o="".endsWith;n(n.P+n.F*a(72)("endsWith"),"String",{endsWith:function(e){var t=i(this,e,"endsWith"),a=arguments.length>1?arguments[1]:void 0,n=r(t.length),c=void 0===a?n:Math.min(r(a),n),l=String(e);return o?o.call(t,l,c):t.slice(c-l.length,c)===l}})},215:function(e){e.exports={data:{allMdx:{edges:[{node:{fields:{slug:"onboarding/one.html",stepNumber:1,short:"Select"}}},{node:{fields:{slug:"onboarding/two.html",stepNumber:2,short:"Clone"}}},{node:{fields:{slug:"onboarding/three.html",stepNumber:3,short:"Design"}}},{node:{fields:{slug:"onboarding/four.html",stepNumber:4,short:"Run"}}},{node:{fields:{slug:"onboarding/success.html",stepNumber:5,short:"Success"}}}]}}}}}]);
//# sourceMappingURL=component---gatsby-cache-gatsby-mdx-mdx-wrappers-dir-5-c-99519-e-43-d-7-bd-6-d-2771-e-717590-a-0-c-5-c-scope-hash-3010-b-3-badc-54-a-9-dfa-4-a-4-c-80-e-419-a-41-b-2-js-b4255ebf123200868425.js.map