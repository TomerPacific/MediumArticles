"use client"

import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { BookmarkIcon, HandIcon as HandClap, MessageSquareIcon, ShareIcon, TwitterIcon } from "lucide-react"
import Link from "next/link"

export default function ArticlePage({ params }: { params: { slug: string } }) {
  return (
    <div className="min-h-screen bg-white">
      <div className="py-4">
        <div className="container">
          <Link href="/" className="text-sm text-muted-foreground hover:underline">
            ← Back to articles
          </Link>
        </div>
      </div>
      <main className="container py-8">
        <article className="mx-auto max-w-2xl space-y-8">
          <header className="space-y-6">
            <h1 className="text-4xl font-bold leading-tight">How To Implement Infinite Scroll In React</h1>
            <div className="flex items-center gap-4">
              <Avatar className="h-12 w-12 border-2 border-black">
                <AvatarImage src="/images/profile.png" alt="Tomer Ben-Rachel" />
                <AvatarFallback>TB</AvatarFallback>
              </Avatar>
              <div>
                <div className="flex items-center gap-2">
                  <Link href="/" className="font-medium hover:underline">
                    Tomer Ben-Rachel
                  </Link>
                </div>
                <div className="text-sm text-muted-foreground">Jan 10, 2023 · 7 min read</div>
              </div>
            </div>
            <div className="flex items-center gap-4">
              <Button variant="ghost" size="sm">
                <TwitterIcon className="mr-2 h-4 w-4" />
                Share on Twitter
              </Button>
              <Button variant="ghost" size="sm">
                <ShareIcon className="mr-2 h-4 w-4" />
                Share
              </Button>
              <Button variant="ghost" size="sm">
                <BookmarkIcon className="mr-2 h-4 w-4" />
                Save
              </Button>
            </div>
          </header>
          <div className="prose prose-lg max-w-none">
            <p>
              Infinite scroll has become a staple in modern web applications. From social media feeds to product
              listings, this pattern provides a seamless user experience by loading content as the user scrolls down the
              page.
            </p>
            <p>
              In this article, I'll show you how to implement infinite scroll in React using the Intersection Observer
              API, a powerful browser feature that makes it easy to detect when an element enters the viewport.
            </p>
            <h2>Understanding the Intersection Observer API</h2>
            <p>
              The Intersection Observer API provides a way to asynchronously observe changes in the intersection of a
              target element with an ancestor element or with the document's viewport. This is perfect for implementing
              infinite scroll, as we can detect when the user has scrolled to the bottom of the page.
            </p>
            <p>Here's a basic example of how to use the Intersection Observer API:</p>
            <pre>
              <code>{`const observer = new IntersectionObserver(
  (entries) => {
    if (entries[0].isIntersecting) {
      // Element is in view, load more content
      loadMoreContent();
    }
  },
  { threshold: 1.0 }
);

// Start observing an element
observer.observe(document.querySelector('#sentinel'));`}</code>
            </pre>
            <h2>Setting Up Our React Component</h2>
            <p>Let's create a React component that implements infinite scroll. We'll start with a basic structure:</p>
            <pre>
              <code>{`import React, { useState, useEffect, useRef } from 'react';

function InfiniteScroll() {
  const [items, setItems] = useState([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const loaderRef = useRef(null);

  // Component logic will go here

  return (
    <div className="infinite-scroll-container">
      {items.map((item) => (
        <div key={item.id} className="item">
          <h2>{item.title}</h2>
          <p>{item.description}</p>
        </div>
      ))}
      {loading && <div className="loader">Loading...</div>}
      {hasMore && <div ref={loaderRef} className="loader-sentinel" />}
    </div>
  );
}`}</code>
            </pre>
            <h2>Implementing the Intersection Observer</h2>
            <p>Now, let's add the Intersection Observer to detect when the user scrolls to the bottom of the list:</p>
            <pre>
              <code>{`useEffect(() => {
  const fetchItems = async () => {
    setLoading(true);
    try {
      // Replace with your API endpoint
      const response = await fetch(\`https://api.example.com/items?page=\${page}\`);
      const newItems = await response.json();
      
      if (newItems.length === 0) {
        setHasMore(false);
      } else {
        setItems((prevItems) => [...prevItems, ...newItems]);
      }
    } catch (error) {
      console.error('Error fetching items:', error);
    } finally {
      setLoading(false);
    }
  };

  fetchItems();
}, [page]);

useEffect(() => {
  const currentLoaderRef = loaderRef.current;
  
  const observer = new IntersectionObserver(
    (entries) => {
      const target = entries[0];
      if (target.isIntersecting && hasMore && !loading) {
        setPage((prevPage) => prevPage + 1);
      }
    },
    { threshold: 1.0 }
  );
  
  if (currentLoaderRef) {
    observer.observe(currentLoaderRef);
  }
  
  return () => {
    if (currentLoaderRef) {
      observer.unobserve(currentLoaderRef);
    }
  };
}, [hasMore, loading]);`}</code>
            </pre>
            <h2>Adding Optimizations</h2>
            <p>To make our infinite scroll implementation more efficient, we can add some optimizations:</p>
            <ol>
              <li>
                <strong>Debouncing:</strong> Prevent multiple fetch requests when the user scrolls quickly.
              </li>
              <li>
                <strong>Error handling:</strong> Properly handle API errors and provide retry functionality.
              </li>
              <li>
                <strong>Loading states:</strong> Show loading indicators to provide feedback to the user.
              </li>
              <li>
                <strong>Virtualization:</strong> For very large lists, consider using a virtualization library like
                react-window to render only the visible items.
              </li>
            </ol>
            <h2>Complete Example</h2>
            <p>Here's a complete example of an infinite scroll component with these optimizations:</p>
            <pre>
              <code>{`import React, { useState, useEffect, useRef, useCallback } from 'react';
import debounce from 'lodash.debounce';

function InfiniteScroll() {
  const [items, setItems] = useState([]);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [hasMore, setHasMore] = useState(true);
  const loaderRef = useRef(null);

  const fetchItems = useCallback(async () => {
    if (loading || !hasMore) return;
    
    setLoading(true);
    setError(null);
    
    try {
      // Replace with your API endpoint
      const response = await fetch(\`https://api.example.com/items?page=\${page}\`);
      
      if (!response.ok) {
        throw new Error(\`HTTP error! Status: \${response.status}\`);
      }
      
      const newItems = await response.json();
      
      if (newItems.length === 0) {
        setHasMore(false);
      } else {
        setItems((prevItems) => [...prevItems, ...newItems]);
        setPage((prevPage) => prevPage + 1);
      }
    } catch (error) {
      setError(error.message);
      console.error('Error fetching items:', error);
    } finally {
      setLoading(false);
    }
  }, [page, loading, hasMore]);

  // Debounced version of fetchItems
  const debouncedFetchItems = useCallback(
    debounce(() => {
      fetchItems();
    }, 300),
    [fetchItems]
  );

  useEffect(() => {
    // Initial fetch
    fetchItems();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    const currentLoaderRef = loaderRef.current;
    
    const observer = new IntersectionObserver(
      (entries) => {
        const target = entries[0];
        if (target.isIntersecting && hasMore && !loading) {
          debouncedFetchItems();
        }
      },
      { threshold: 0.1, rootMargin: '100px' }
    );
    
    if (currentLoaderRef) {
      observer.observe(currentLoaderRef);
    }
    
    return () => {
      if (currentLoaderRef) {
        observer.unobserve(currentLoaderRef);
      }
      debouncedFetchItems.cancel();
    };
  }, [hasMore, loading, debouncedFetchItems]);

  return (
    <div className="infinite-scroll-container">
      {items.map((item) => (
        <div key={item.id} className="item">
          <h2>{item.title}</h2>
          <p>{item.description}</p>
        </div>
      ))}
      
      {error && (
        <div className="error">
          <p>Error: {error}</p>
          <button onClick={fetchItems}>Retry</button>
        </div>
      )}
      
      {loading && <div className="loader">Loading more items...</div>}
      
      {hasMore && <div ref={loaderRef} className="loader-sentinel" />}
      
      {!hasMore && <div className="end-message">No more items to load</div>}
    </div>
  );
}`}</code>
            </pre>
            <h2>Conclusion</h2>
            <p>
              Implementing infinite scroll in React using the Intersection Observer API provides a smooth and efficient
              user experience. By following the approach outlined in this article, you can create a performant infinite
              scroll implementation that works well even with large datasets.
            </p>
            <p>
              Remember to consider the user experience aspects of infinite scroll, such as providing clear loading
              indicators and handling edge cases like errors and empty states. With these considerations in mind, you
              can create a polished infinite scroll implementation that enhances your application.
            </p>
          </div>
          <div className="flex items-center justify-between border-t border-b py-6">
            <div className="flex items-center gap-4">
              <Button variant="outline" size="icon" className="rounded-full">
                <HandClap className="h-5 w-5" />
                <span className="sr-only">Clap</span>
              </Button>
              <span className="text-sm text-muted-foreground">342</span>
            </div>
            <div className="flex items-center gap-4">
              <Button variant="ghost" size="sm">
                <BookmarkIcon className="mr-2 h-4 w-4" />
                Save
              </Button>
              <Button variant="ghost" size="sm">
                <ShareIcon className="mr-2 h-4 w-4" />
                Share
              </Button>
            </div>
          </div>
          <section className="space-y-6">
            <h2 className="text-2xl font-bold">Responses (24)</h2>
            <div className="flex gap-4">
              <Avatar className="h-10 w-10">
                <AvatarImage src="/placeholder.svg?height=40&width=40" alt="Your profile" />
                <AvatarFallback>YP</AvatarFallback>
              </Avatar>
              <div className="flex-1">
                <textarea
                  className="w-full resize-none rounded-md border p-3 text-sm"
                  placeholder="Write a response..."
                  rows={3}
                ></textarea>
                <div className="mt-2 flex justify-end">
                  <Button>Respond</Button>
                </div>
              </div>
            </div>
            <Card className="border-0 shadow-none">
              <CardContent className="p-0 space-y-6">
                <div className="flex gap-4">
                  <Avatar className="h-10 w-10">
                    <AvatarImage src="/placeholder.svg?height=40&width=40" alt="Alex Chen" />
                    <AvatarFallback>AC</AvatarFallback>
                  </Avatar>
                  <div className="space-y-2">
                    <div className="flex items-center gap-2">
                      <span className="font-medium">Alex Chen</span>
                      <span className="text-xs text-muted-foreground">3 days ago</span>
                    </div>
                    <p className="text-sm">
                      Great article! I've been struggling with implementing infinite scroll efficiently. Your
                      explanation of the Intersection Observer API really helped me understand how to approach this. I
                      implemented it in my project and it works perfectly now.
                    </p>
                    <div className="flex items-center gap-4">
                      <Button variant="ghost" size="sm" className="h-8 px-2">
                        <HandClap className="mr-1 h-4 w-4" />
                        <span>15</span>
                      </Button>
                      <Button variant="ghost" size="sm" className="h-8 px-2">
                        <MessageSquareIcon className="mr-1 h-4 w-4" />
                        <span>Reply</span>
                      </Button>
                    </div>
                  </div>
                </div>
                <div className="flex gap-4">
                  <Avatar className="h-10 w-10">
                    <AvatarImage src="/placeholder.svg?height=40&width=40" alt="Maria Lopez" />
                    <AvatarFallback>ML</AvatarFallback>
                  </Avatar>
                  <div className="space-y-2">
                    <div className="flex items-center gap-2">
                      <span className="font-medium">Maria Lopez</span>
                      <span className="text-xs text-muted-foreground">1 day ago</span>
                    </div>
                    <p className="text-sm">
                      Have you considered using react-query or SWR for this? They have built-in support for pagination
                      and can make the implementation even cleaner. Would love to see a follow-up article comparing
                      different approaches!
                    </p>
                    <div className="flex items-center gap-4">
                      <Button variant="ghost" size="sm" className="h-8 px-2">
                        <HandClap className="mr-1 h-4 w-4" />
                        <span>8</span>
                      </Button>
                      <Button variant="ghost" size="sm" className="h-8 px-2">
                        <MessageSquareIcon className="mr-1 h-4 w-4" />
                        <span>Reply</span>
                      </Button>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          </section>
        </article>
      </main>
    </div>
  )
}
