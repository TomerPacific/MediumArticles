//
//  ViewController.m
//  JStoiOSCommunicator
//
//  Created by Tomer Ben-Rachel on 08/07/2018.
//  Copyright © 2018 Tomer Ben-Rachel. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    WKUserContentController *userContentController = [[WKUserContentController alloc] init];
    WKWebViewConfiguration *configuration = [[WKWebViewConfiguration alloc] init];
    [userContentController addScriptMessageHandler: self name:@"myOwnJSHandler"];
    configuration.userContentController = userContentController;
    
    CGRect frame = CGRectMake([[UIScreen mainScreen] bounds].origin.x, [[UIScreen mainScreen] bounds].origin.y, [[UIScreen mainScreen] bounds].size.width, [[UIScreen mainScreen] bounds].size.height);
    
    _webview = [[WKWebView alloc] initWithFrame:frame configuration:configuration];
    
    NSString *urlPath = [[NSBundle mainBundle] pathForResource:@"index" ofType:@"html"];
    
    NSURL *url = [NSURL fileURLWithPath:urlPath isDirectory:NO];
    
    [_webview loadFileURL:url allowingReadAccessToURL:url];

    [self.view addSubview:_webview];

}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)userContentController:(WKUserContentController *)userContentController didReceiveScriptMessage:(WKScriptMessage *)message {
    
    NSLog(@"%@", message.body);
}


@end
