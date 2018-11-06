chrome.browserAction.onClicked.addListener(function(){       
    chrome.tabs.create({
        'url': chrome.runtime.getURL("popup.html#window")
    });
});