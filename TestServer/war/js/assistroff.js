/*
 * www.assistro.com, 2012
 *
 * Assistro widget code.
 * 
 * author: Irfan Manzoor, irfansh79@gmail.com 
 *
 */

(function() {
	"use strict";

	var assistro = {}, $;

	if (typeof assistro === 'undefined') {
		assistro = {};
	}

	$ = assistro.$ = (function() {
		var CSS_SELECTOR_REGEXP = /^([\.\#]?[\w\-]+ *)+$/, Wrapper,

		_bindEvent;

		function _getFirstElementFromCollection() {
			if ((typeof this.collection !== 'undefined')
					&& (this.collection.length > 0)) {
				return this.collection[0];
			} else {
				return null;
			}
		}

		_bindEvent = function(eventName, func) {
			var _bindFunc;

			if (typeof document.addEventListener !== 'undefined') {
				_bindFunc = function(elem, eventName, func) {
					elem.addEventListener(eventName, func);
				};

			} else if (typeof document.attachEvent !== 'undefined') {
				_bindFunc = function(elem, eventName, func) {
					elem.attachEvent('on' + eventName, func);
				};

			} else {
				_bindFunc = function(elem, eventName, func) {
					elem['on' + eventName] = func;
				};
			}

			_bindEvent = function(eventName, func) {
				var _this = this;
				assistro.utils.map(this.collection, function(elem, index) {
					_bindFunc(elem, eventName, function(e) {
						func.call(elem, e);
					});
				});
			};

			_bindEvent.call(this, eventName, func);
		};

		Wrapper = (function() {
			var _constructWrapperFromCSSSelector;

			_constructWrapperFromCSSSelector = function(selector) {
				if (typeof document.querySelectorAll !== 'undefined') {
					_constructWrapperFromCSSSelector = function(selector) {
						try {
							this.collection = Array.prototype.slice.call(
									document.querySelectorAll(selector), 0);
						} catch (e) {
							if (e instanceof TypeError) {
								this.collection = document
										.querySelectorAll(selector);
							} else {
								throw e;
							}
						}
					};

					_constructWrapperFromCSSSelector.call(this, selector);

				} else {
					throw new Error(assistro.config.ERR_QUERY_SELECTOR_ALL);
				}
			};

			function _constructWrapperFromHTMLString(HTMLString) {
				var div = document.createElement('div');

				div.innerHTML = HTMLString;

				this.collection = Array.prototype.slice.call(div.childNodes, 0);
			}

			function _constructWrapperFromHTMLElement(HTMLElement) {
				this.collection = [ HTMLElement ];
			}

			return function(arg) {
				if ((typeof arg === 'string')
						&& (CSS_SELECTOR_REGEXP.test(arg) === true)) {
					_constructWrapperFromCSSSelector.call(this, arg);

				} else if (typeof arg === 'string') {
					_constructWrapperFromHTMLString.call(this, arg);

				} else if ((typeof arg === 'object')
						&& (arg instanceof HTMLElement)) {
					_constructWrapperFromHTMLElement.call(this, arg);

				} else {
					throw new Error(assistro.config.ERR_WRONG_ARGUMENT_$);
				}
			};
		}());

		Wrapper.prototype.val = function(arg) {
			var _firstEl = _getFirstElementFromCollection.call(this);

			if (_firstEl !== null) {
				if (typeof arg === 'undefined') {
					return _firstEl.value;
				} else {
					return _firstEl.value = arg;
				}
			} else {
				return;
			}
		};

		Wrapper.prototype.remove = function() {
			assistro.utils.map(this.collection, function(elem, index) {
				var parent = elem.parentNode;

				if (typeof parent !== 'undefined') {
					parent.removeChild(elem);
				}
			});

			return this;
		};

		Wrapper.prototype.css = (function() {
			function _transformName(name) {
				return name.replace(/-(\w)/, function(match, firstLetter) {
					return firstLetter.toUpperCase();
				});
			}

			function _applyStyle(name, value) {
				name = _transformName(name);

				this.style[name] = value;
			}

			function _processStyle(obj) {
				var key, value;

				for (key in obj) {
					if (obj.hasOwnProperty(key) === true) {
						value = obj[key];

						_applyStyle.call(this, key, value);
					}
				}
			}

			return function() {
				var obj;

				switch (arguments.length) {
				case 1:
					obj = arguments[0];

					break;
				case 2:
					obj = {};
					obj[arguments[0]] = arguments[1];

					break;
				default:
					return;
				}

				assistro.utils.map(this.collection, function(elem, index) {
					_processStyle.call(elem, obj);
				});

				return this;
			};
		}());

		Wrapper.prototype.show = function() {
			this.css('display', 'block');

			return this;
		};

		Wrapper.prototype.hide = function() {
			this.css('display', 'none');

			return this;
		};

		Wrapper.prototype.animate = function(propertyName, start, end, time) {
			var promise = new assistro.utils.Promise(), increment = (end - start)
					/ time * assistro.config.ANIMATION_STEP, value = start, _this = this, timerFunc = function() {
				if (Math.abs(value - start) > Math.abs(end - start)) {
					value = end;

					promise.resolve(_this);

				} else if (Math.abs(value - end) > Math.abs(end - start)) {
					value = start;

					promise.resolve(_this);

				} else {
					value += increment;

					setTimeout(timerFunc, assistro.config.ANIMATION_STEP);
				}

				_this.css(propertyName, value);
			};

			if (start === end) {
				setTimeout(function() {
					promise.resolve(_this);
				}, time);

			} else {
				timerFunc();
			}

			return promise;
		};

		Wrapper.prototype.blink = (function() {
			return function() {
				this.animate('opacity', 0, 1, 2000).then(function() {
					return this.animate('opacity', 1, 1, 2000);
				}).then(function() {
					return this.animate('opacity', 1, 0, 2000);
				}).then(function() {
					this.hide();
				});

				this.show();

				return this;
			};
		}());

		Wrapper.prototype.insertBefore = function(arg) {
			var _firstEl = _getFirstElementFromCollection.call(arg = assistro
					.$(arg)), _parentEl;

			if (_firstEl !== null) {
				_parentEl = _firstEl.parentNode;

				assistro.utils.map(this.collection, function(elem, index) {
					_parentEl.insertBefore(elem, _firstEl);
				});
			}

			return this;
		};

		Wrapper.prototype.append = function(arg) {
			var _firstEl = _getFirstElementFromCollection.call(this);

			arg = assistro.$(arg);

			assistro.utils.map(arg.collection, function(elem, index) {
				_firstEl.appendChild(elem);
			});

			return this;
		};

		Wrapper.prototype.scrollDown = function() {
			assistro.utils.map(this.collection, function(elem, index) {
				if ((typeof elem.scrollTop !== 'undefined')
						&& (typeof elem.scrollHeight !== 'undefined')) {
					elem.scrollTop = elem.scrollHeight;
				}

			});

			return this;
		};

		Wrapper.prototype.click = function(func) {
			_bindEvent.call(this, 'click', func);

			return this;
		};

		Wrapper.prototype.keypress = function(func) {
			_bindEvent.call(this, 'keypress', func);

			return this;
		};

		Wrapper.prototype.ready = function(func) {
			_bindEvent.call(this, 'load', func);

			return this;
		};

		Wrapper.prototype.focus = function(func) {
			assistro.utils.map(this.collection, function(elem, index) {
				if (typeof elem.focus === 'function') {
					elem.focus();
				}
			});

			return this;
		};

		return function(arg) {
			if (arg instanceof Wrapper) {
				return arg;
			} else {
				return new Wrapper(arg);
			}
		};

	}());

	if (typeof assistro === 'undefined') {
		assistro = {};
	}

	if (typeof assistro.config === 'undefined') {
		assistro.config = {};
	}

//	 assistro.config.SERVER_URL = 'http://iamassistro.appspot.com/';
	assistro.config.SERVER_URL = 'http://localhost:8888/';
	assistro.config.photoUrl = ' ';
	assistro.config.chkPro = false;

	assistro.config.EMAIL_VALIDITY_REGEXP = /^[\w\d.%-]+@[\w\d.-]+\.[\w]{2,4}$/;

	assistro.config.EXPIRE_MILLISECONDS = 24 * 60 * 60 * 1000;

	assistro.config.ANIMATION_STEP = 50;

	assistro.config.CLOSE_MESSAGE = 'User has refreshed or leaved the page'

	assistro.config.ALLERT_MESSAGE = '<div class="quick-alert" style="position:absolute;z-index:300;margin-top:10px;margin-left:105px;font-size:14px;border:solid 1px #CCCCCC;background-color: #808080;color:#DC143C">Alert! Email sent.</div>'

	assistro.config.ERR_QUERY_SELECTOR_ALL = 'document.querySelectorAll is not supported by browser';

	assistro.config.ERR_WRONG_ARGUMENT_$ = 'Wrong argument for assistro.$';

	assistro.config.ERR_DATA_TRANSPORT = 'Data transmitting transport isn\'t available';

	if (typeof assistro === 'undefined') {
		assistro = {};
	}

	if (typeof assistro.utils === 'undefined') {
		assistro.utils = {};
	}

	assistro.utils.reduce = (function() {
		if (typeof Array.prototype.reduce !== 'undefined') {
			return function(arr, func, memoInit) {
				return Array.prototype.reduce.call(arr, func, memoInit);
			};
		} else {
			return function(arr, func, memoInit) {
				var i, max_i;

				for (i = 0, max_i = arr.length; i < max_i; i += 1) {
					memoInit = func(arr[i], memoInit);
				}

				return memoInit;
			};
		}
	}());

	assistro.utils.map = (function() {
		if (typeof Array.prototype.map !== 'undefined') {
			return function(arr, func) {
				return Array.prototype.map.call(arr, func);
			};
		} else {
			return function(arr, func) {
				var i, max_i, returnArr = [];

				for (i = 0, max_i = arr.length; i < max_i; i += 1) {
					returnArr.push(func(arr[i], i));
				}

				return returnArr;
			};
		}
	}());

	assistro.utils.mergeObj = (function() {
		function _mergeObj(obj1, obj2) {
			var key, value;

			for (key in obj2) {
				if (obj2.hasOwnProperty(key) === true) {
					value = obj2[key];

					if ((value !== null) && (typeof value === 'object')) {
						if ((typeof obj1[key] !== 'object')
								|| (obj1[key] === null)) {
							obj1[key] = {};
						}

						_mergeObj(obj1[key], value);
					} else {
						obj1[key] = value;
					}
				}
			}

			return obj1;
		}

		return function() {
			var target = arguments[0], sources = Array.prototype.slice.call(
					arguments, 1);

			return assistro.utils.reduce(sources, _mergeObj, target);
		};
	}());

	assistro.utils.makeid = function() {
		var textArr = [], possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789', i;

		for (i = 0; i < 16; i += 1) {
			textArr.push(possible.charAt(Math.floor(Math.random()
					* possible.length)));
		}

		return textArr.join('');
	};

	assistro.utils.trim = function(str) {
		return str.replace(/^\s*/, '').replace(/\s*$/, '');
	};

	assistro.utils.Promise = (function() {
		function Promise() {
			this.resolved = false;

			this._callStack = [];

			this._resolvedArgs = null;
		}

		function _execute(callbacks, innerPromise, context) {
			var _this = this, _promises = assistro.utils.map(callbacks,
					function(func, index) {
						return func.apply(_this._resolveContext,
								_this._resolvedArgs);
					});

			if (_promises[0] instanceof assistro.utils.Promise) {
				_promises[0].then(function() {
					innerPromise.resolve(this);
				});
			}
		}

		Promise.prototype.resolve = function() {
			var _this = this;

			this._resolveContext = arguments[0];
			this._resolvedArgs = Array.prototype.slice.call(arguments, 1);

			this.resolved = true;

			_execute.call(this, this._callStack, this._innerPromise,
					this._resolveContext);
		};

		Promise.prototype.then = function() {
			var args = Array.prototype.slice.call(arguments, 0), _this = this;

			this._callStack = this._callStack.concat(args);
			this._innerPromise = new assistro.utils.Promise();

			if (this.resolved === true) {
				_execute.call(this, args, this._innerPromise,
						this._resolveContext);
			}

			return this._innerPromise;
		};

		return Promise;
	}());

	if (typeof assistro === 'undefined') {
		assistro = {};
	}

	if (typeof assistro.cookies === 'undefined') {
		assistro.cookies = {};
	}

	assistro.cookies.getCookie = function(c_name) {
		var i, x, y, ARRcookies = document.cookie.split(';');

		for (i = 0; i < ARRcookies.length; i++) {
			x = ARRcookies[i].substr(0, ARRcookies[i].indexOf('='));
			y = ARRcookies[i].substr(ARRcookies[i].indexOf('=') + 1);

			x = x.replace(/^\s+|\s+$/g, "");

			if (x === c_name) {
				return unescape(y);
			}
		}
	};

	assistro.cookies.setCookie = function(c_name, value, exdays) {
		var exdate = new Date(), c_value;

		exdate.setDate(exdate.getDate() + exdays);

		c_value = escape(value)
				+ (((exdays === null) || (typeof exdays === 'undefined')) ? ''
						: '; expires=' + exdate.toUTCString());
		document.cookie = c_name + '=' + c_value;

		return value;
	};

	assistro.cookies.checkCookie = function(cookieName, cookieGenerator) {
		var cookieValue = assistro.cookies.getCookie(cookieName);

		if ((typeof cookieValue !== 'undefined') && (cookieValue !== null)
				&& (cookieValue !== '')) {
			return cookieValue;
		} else {
			if (typeof cookieGenerator === 'function') {
				cookieValue = cookieGenerator.call();
			} else {
				cookieValue = cookieGenerator;
			}

			return assistro.cookies.setCookie(cookieName, cookieValue, 1);
		}
	};

	if (typeof assistro === 'undefined') {
		assistro = {};
	}

	if (typeof assistro.dataTransport === 'undefined') {
		assistro.dataTransport = {};
	}

	assistro.dataTransport.sendData = (function() {
		var PARAM_DEFAULTS = {
			verb : 'GET',
			service : 'GetMessage',

			callback : function() {

			},

			errorCallback : function() {
				throw new Error(assistro.config.ERR_DATA_TRANSPORT);
			}

		},

		_setHeaders = null, transport;

		function _getTransport() {
			if (typeof XDomainRequest !== 'undefined') {
				return XDomainRequest;

			} else if (typeof XMLHttpRequest !== 'undefined') {
				return XMLHttpRequest;

			} else {
				return null;
			}
		}

		function _preprocessParams(params) {
			return assistro.utils.mergeObj({}, PARAM_DEFAULTS, params);
		}

		function _getQueryStringArr(params) {
			var returnArr = [], firstItemFlag = true, key, value;

			for (key in params.data) {
				if (params.data.hasOwnProperty(key) === true) {
					value = params.data[key];

					if (!firstItemFlag) {
						returnArr.push('&');
					} else {
						firstItemFlag = false;
					}

					returnArr.push(key + '=');

					if ((typeof value === 'object') && (value !== null)) {
						returnArr.push(JSON.stringify(value));
					} else {
						returnArr.push(value);
					}
				}
			}

			return returnArr;
		}

		function _constructURL(params, includeQueryString) {
			var dataItem;

			return [ assistro.config.SERVER_URL, params.service ].concat(
					(includeQueryString) ? [ '?' ]
							.concat(_getQueryStringArr(params)) : []).join('');
		}

		function initialSetup(params) {
			if (typeof transport === 'undefined') {
				transport = _getTransport();
			}

			if (transport !== null) {
				if (typeof transport.prototype.setRequestHeader !== 'undefined') {
					_setHeaders = transport.prototype.setRequestHeader;

				} else {
					_setHeaders = function() {

					};
				}

				assistro.dataTransport.sendData = sendDataImplementation;
				assistro.dataTransport.sendData(params);

			} else {
				throw new Error(assistro.config.ERR_DATA_TRANSPORT);
			}
		}

		function sendDataImplementation(params) {
			var transportInstance = new transport(), requestBody;

			params = _preprocessParams(params);

			switch (params.verb) {
			case 'GET':
				transportInstance
						.open(params.verb, _constructURL(params, true));
				requestBody = '';

				break;

			case 'POST':
				transportInstance.open(params.verb,
						_constructURL(params, false));
				requestBody = _getQueryStringArr(params).join('');

				break;
			default:

			}

			transportInstance.onload = function() {
				var responseObj = JSON.parse(transportInstance.responseText);

				if ((typeof responseObj !== 'undefined')
						&& (responseObj !== null)) {
					params.callback(responseObj);
				}
			};

			transportInstance.onerror = function() {
				params.errorCallback.call();
			};

			_setHeaders.call(transportInstance, 'Content-Type',
					'application/x-www-form-urlencoded');
			_setHeaders.call(transportInstance, 'Accept',
					'application/json, text/javascript, */*; q=0.01');

			transportInstance.send(requestBody);
		}

		return initialSetup;
	}());
	
	
if (typeof assistro === 'undefined') {
  assistro = {};
}

if (typeof assistro.eventHandlers === 'undefined') {
  assistro.eventHandlers = {};
}

assistro.eventHandlers.sendOfflineMsgClick = (function () {
    var jQueryname, jQueryemail, jQueryoffline_message,
        jQuerynameVal, jQueryemailVal, message;

    function _processResult (result) {
        $(assistro.config.ALLERT_MESSAGE).insertBefore('#chat-off-content .email-send-btn').blink();
        
        jQueryname.val('');
        jQueryemail.val('');
        jQueryoffline_message.val('');
    }

    return function () {
        jQueryname = $('#chat-off-content #off_name');
        jQueryemail = $('#chat-off-content #off_email');
        jQueryoffline_message = $('#chat-off-content #off_msg');
       
        jQuerynameVal = jQueryname.val();
        jQueryemailVal = jQueryemail.val();

        message = assistro.utils.trim(jQueryoffline_message.val());

        if (message.length === 0)
            return false;

        assistro.dataTransport.sendData({
            verb: 'POST',
            service: 'SendEmail',
            
            data: {
                subject: jQuerynameVal,
                email: jQueryemailVal,
                message: message, 
                code: virtism_config.widget_code 
            },
            
            callback: _processResult,
            
            errorCallback: _processResult
        });
    };
} ());

(function () {
    //transport;
    // show offline widget from collapsed state
    $('#offline_btn').click(function () {
        $('#offline_btn').hide();
        $('#chat-off-wrapper').show();
    });

    // Close offline widget collapsed state
    $('div.chat_container div.collapsed_offline .header .buttons .cross').click(function () {
        $('div.chat_container div.collapsed_offline').remove();
    });

    // Minimize the Offline Chat Dialog to Collapsed State
    $('#chat-off-top-window .minimize').click(function () {
        $('#chat-off-wrapper').hide();

        $('#offline_btn').show();
    });

    // Close the Offline Chat Dialog
    $('#chat-off-top-window .cross').click(function () {
        $('#chat-off-wrapper').remove();
    });

    // Minimize the Online Chat Dialog to Collapsed State
    $('#chat-app-top-window .minimize').click(function () {
        $('#chat-app-wrapper').hide();
        $('#live-chat-btn').show();
    });
    //send offline email
    $('#chat-off-content .email-send-btn').click(function() {
		assistro.eventHandlers.sendOfflineMsgClick.call(this);
	}); 
} ());
} ());
