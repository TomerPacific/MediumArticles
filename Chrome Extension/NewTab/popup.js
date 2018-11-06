//chrome.tabs.create({url: 'popup.html'}) 

// chrome.tabs.create({'url': chrome.extension.getURL('popup.html')}, function(tab) {
//     // Tab opened.
// });

chrome.browserAction.onClicked.addListener(function(tab) {
	chrome.tabs.create({'url': chrome.extension.getURL('popup.html')}, function(tab) {
		// Tab opened.
	});
});