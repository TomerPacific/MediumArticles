//
//  ViewController.h
//  JStoiOSCommunicator
//
//  Created by Tomer Ben-Rachel on 08/07/2018.
//  Copyright © 2018 Tomer Ben-Rachel. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <WebKit/WebKit.h>

@interface ViewController : UIViewController <WKScriptMessageHandler>

@property(nonatomic, strong) WKWebView *webview;

@end

