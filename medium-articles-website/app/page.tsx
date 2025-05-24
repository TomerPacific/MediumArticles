import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import Link from "next/link"
import { CalendarIcon } from "lucide-react"

const articles = [
  {
    title: "Migrating to Kotlin 2 In Jetpack Compose",
    url: "https://medium.com/proandroiddev/migrating-to-kotlin-2-0-in-your-jetpack-compose-project-315426b88d66",
    category: "Android Development",
    tags: ["Kotlin", "Jetpack Compose", "Migration"],
    date: "2024", // Approximate year based on Kotlin 2.0 release
  },
  {
    title: "Ktor In Jetpack Compose",
    url: "https://medium.com/proandroiddev/using-ktor-in-jetpack-compose-e00b1b32eef0",
    category: "Android Development",
    tags: ["Ktor", "Jetpack Compose", "Networking"],
    date: "2024", // Approximate year
  },
  {
    title: "Localization In Flutter",
    url: "https://levelup.gitconnected.com/supporting-multiple-languages-in-your-flutter-application-e814bbaebdfc",
    category: "Flutter Development",
    tags: ["Flutter", "Localization", "Internationalization"],
    date: "2023", // Approximate year
  },
  {
    title: "Tooltips In Jetpack Compose",
    url: "https://levelup.gitconnected.com/tooltips-in-jetpack-compose-8642dbe51b72",
    category: "Android Development",
    tags: ["Jetpack Compose", "Tooltips", "UI"],
    date: "2023", // Approximate year
  },
  {
    title: "Migrating a Flutter Application from GetIt to Bloc",
    url: "https://medium.com/flutter-community/migrating-a-flutter-application-from-getit-to-bloc-c737ffebed0a",
    category: "Flutter Development",
    tags: ["Flutter", "GetIt", "Bloc", "Migration"],
    date: "2023", // Approximate year
  },
  {
    title: "Migrating From Play Core Library",
    url: "https://medium.com/p/0b4afd3fca1a",
    category: "Android Development",
    tags: ["Android", "Migration", "Play Core"],
    date: "2023", // Approximate year
  },
  {
    title: "Make Your Flutter Package Privacy Manifest Compatible",
    url: "https://levelup.gitconnected.com/make-your-flutter-package-privacy-manifest-compatible-52b2da5eabf3",
    category: "Flutter Development",
    tags: ["Flutter", "Privacy", "Packages"],
    date: "2023", // Approximate year based on iOS Privacy Manifest introduction
  },
  {
    title: "Working On A Multiple Library Project",
    url: "https://proandroiddev.com/working-on-a-multiple-library-project-b01af2625391",
    category: "Software Development",
    tags: ["Libraries", "Project Management", "Architecture"],
    date: "2023", // Approximate year
  },
  {
    title: "Testing Proto DataStore",
    url: "https://proandroiddev.com/testing-proto-datastore-63580efbf5fc",
    category: "Android Development",
    tags: ["Android", "Testing", "DataStore"],
    date: "2023", // Approximate year
  },
  {
    title: "iOS Privacy Manifest",
    url: "https://medium.com/p/6b56a895d122",
    category: "iOS Development",
    tags: ["iOS", "Privacy", "Manifest"],
    date: "2023", // Approximate year based on iOS Privacy Manifest introduction
  },
  {
    title: "Flutter - Migrating to V2 Embedding",
    url: "https://medium.com/p/964fd4ed9e5a",
    category: "Flutter Development",
    tags: ["Flutter", "Migration", "V2 Embedding"],
    date: "2023", // Approximate year
  },
  {
    title: "Proto DataStore",
    url: "https://medium.com/p/36ae997d00f2",
    category: "Android Development",
    tags: ["Android", "DataStore", "Data Storage"],
    date: "2022", // Approximate year
  },
  {
    title: "Location Permissions In Jetpack Compose",
    url: "https://medium.com/p/bb9b3dae8d76#1585-a6ff6a90d783",
    category: "Android Development",
    tags: ["Jetpack Compose", "Permissions", "Location"],
    date: "2022", // Approximate year
  },
  {
    title: "Firebase Remote Configurations",
    url: "https://medium.com/p/ff1d7161bb01#4817-54e24931e939",
    category: "Backend Development",
    tags: ["Firebase", "Remote Config", "Configuration"],
    date: "2022", // Approximate year
  },
  {
    title: "Tabs In Jetpack Compose",
    url: "https://proandroiddev.com/tabs-in-jetpack-compose-81b1496c97dc",
    category: "Android Development",
    tags: ["Jetpack Compose", "Tabs", "UI"],
    date: "2022", // Approximate year
  },
  {
    title: "Serialization with Kotlin and Jetpack Compose",
    url: "https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59",
    category: "Android Development",
    tags: ["Kotlin", "Jetpack Compose", "Serialization"],
    date: "2022", // Approximate year
  },
  {
    title: "Hide Your API Keys In Android",
    url: "https://proandroiddev.com/hide-your-api-keys-in-android-d4c52446d0bc",
    category: "Android Development",
    tags: ["Android", "Security", "API Keys"],
    date: "2022", // Approximate year
  },
  {
    title: "Flutter In App Update",
    url: "https://medium.com/flutter-community/in-app-update-the-flutter-way-2f25e4a02c02",
    category: "Flutter Development",
    tags: ["Flutter", "Updates", "Play Store"],
    date: "2022", // Approximate year
  },
  {
    title: "Heroku Alternatives",
    url: "https://levelup.gitconnected.com/the-end-of-free-dynos-at-heroku-272fdf2d78c4",
    category: "DevOps",
    tags: ["Heroku", "Deployment", "Cloud"],
    date: "2022", // Approximate year based on Heroku free tier removal
  },
  {
    title: "Google Play SDK Index Breakdown",
    url: "https://proandroiddev.com/google-play-sdk-index-breakdown-6203000d9018",
    category: "Android Development",
    tags: ["Google Play", "SDK", "Analytics"],
    date: "2022", // Approximate year
  },
  {
    title: "Is Jetpack Compose Ready For You?",
    url: "https://medium.com/better-programming/is-jetpack-compose-ready-for-you-eae6c93ad3f8",
    category: "Android Development",
    tags: ["Jetpack Compose", "Android", "UI"],
    date: "2021", // Approximate year based on Jetpack Compose 1.0 release
  },
  {
    title: "Flutter Alarm Manager",
    url: "https://medium.com/flutter-community/flutter-alarmmanager-f184671240cb",
    category: "Flutter Development",
    tags: ["Flutter", "Alarm Manager", "Background Tasks"],
    date: "2021", // Approximate year
  },
  {
    title: "Say Goodbye to your Advertising Id",
    url: "https://medium.com/adventures-in-consumer-technology/say-bye-bye-to-your-advertising-id-8d8946a9af83",
    category: "Privacy",
    tags: ["Privacy", "Advertising", "Mobile"],
    date: "2021", // Approximate year based on advertising ID policy changes
  },
  {
    title: "Detecting ANRs In Your Application",
    url: "https://proandroiddev.com/detecting-anrs-in-your-application-f7ee90fa3cfd",
    category: "Android Development",
    tags: ["Android", "ANR", "Performance"],
    date: "2021", // Approximate year
  },
  {
    title: "The State Of The Flutter",
    url: "https://medium.com/flutter-community/the-state-of-the-flutter-4a178325cced",
    category: "Flutter Development",
    tags: ["Flutter", "State Management", "Architecture"],
    date: "2021", // Approximate year
  },
  {
    title: "Custom Icons In Flutter",
    url: "https://medium.com/flutter-community/how-to-add-custom-icons-to-your-flutter-application-463dbd35fd39",
    category: "Flutter Development",
    tags: ["Flutter", "Icons", "UI Design"],
    date: "2021", // Approximate year
  },
  {
    title: "How Aspiration Killed My Motivation",
    url: "https://hackernoon.com/how-aspiration-killed-my-motivation",
    category: "Personal",
    tags: ["Motivation", "Career", "Personal Growth"],
    date: "2021", // Approximate year
  },
  {
    title: "GitHub Actions",
    url: "https://proandroiddev.com/automating-your-android-development-using-github-actions-aad6c6ec9ea2",
    category: "DevOps",
    tags: ["GitHub Actions", "CI/CD", "Automation"],
    date: "2021", // Approximate year
  },
  {
    title: "Camera2 API",
    url: "https://proandroiddev.com/camera2-everything-you-wanted-to-know-2501f9fd846a",
    category: "Android Development",
    tags: ["Android", "Camera", "API"],
    date: "2021", // Approximate year
  },
  {
    title: "Local Notifications In Flutter",
    url: "https://medium.com/flutter-community/local-notifications-in-flutter-746eb1d606c6",
    category: "Flutter Development",
    tags: ["Flutter", "Notifications", "Local"],
    date: "2021", // Approximate year
  },
  {
    title: "Constructors In Dart",
    url: "https://www.freecodecamp.org/news/constructors-in-dart/",
    category: "Flutter Development",
    tags: ["Dart", "Constructors", "Programming"],
    date: "2021", // Approximate year
  },
  {
    title: "Deploy A Routed React Application To GitHub Pages",
    url: "https://betterprogramming.pub/how-to-deploy-a-routed-react-app-to-github-pages-9a40a31a0afc",
    category: "Web Development",
    tags: ["React", "GitHub Pages", "Deployment"],
    date: "2021", // Approximate year
  },
  {
    title: "Using Nodemailer To Send Emails",
    url: "https://medium.com/better-programming/using-nodemailer-to-send-emails-from-your-node-js-server-d726f73d0439",
    category: "Backend Development",
    tags: ["Node.js", "Email", "Nodemailer"],
    date: "2020", // Approximate year
  },
  {
    title: "Flutter Native - JS Communication Bridge",
    url: "https://medium.com/flutter-community/js-native-communication-bridge-in-flutter-f94b65913df1",
    category: "Flutter Development",
    tags: ["Flutter", "JavaScript", "Native Bridge"],
    date: "2020", // Approximate year
  },
  {
    title: "Authenticate Users and Save Data in a Database Using Firebase",
    url: "https://medium.com/better-programming/authenticate-users-and-save-data-in-a-database-using-firebase-5a7e8828a5f8",
    category: "Backend Development",
    tags: ["Firebase", "Authentication", "Database"],
    date: "2020", // Approximate year
  },
  {
    title: "IDFA And You",
    url: "https://medium.com/macoclock/idfa-and-you-7907c7336119",
    category: "iOS Development",
    tags: ["iOS", "IDFA", "Privacy"],
    date: "2020", // Approximate year based on iOS 14 IDFA changes
  },
  {
    title: "EditText - User Input Done Right",
    url: "https://proandroiddev.com/edittext-user-input-done-right-9efebe877091",
    category: "Android Development",
    tags: ["Android", "EditText", "User Input"],
    date: "2020", // Approximate year
  },
  {
    title: "Navigation Component",
    url: "https://proandroiddev.com/android-navigation-component-fc783c03bb8d",
    category: "Android Development",
    tags: ["Android", "Navigation", "Architecture"],
    date: "2020", // Approximate year
  },
  {
    title: "What You Might Not Know About The AlertDialog",
    url: "https://proandroiddev.com/what-you-might-not-know-about-the-alertdialog-2bdc55f3d907",
    category: "Android Development",
    tags: ["Android", "AlertDialog", "UI Components"],
    date: "2020", // Approximate year
  },
  {
    title: "Why Interviewing As A Developer Is Complete Nonesense",
    url: "https://hackernoon.com/why-interviewing-as-a-developer-is-complete-nonsense-qt6q3yb4",
    category: "Career Development",
    tags: ["Interviews", "Career", "Hiring"],
    date: "2020", // Approximate year
  },
  {
    title: "In App Updates",
    url: "https://android.jlelse.eu/in-app-updates-6de58dab26ce",
    category: "Android Development",
    tags: ["Android", "Updates", "Play Store"],
    date: "2020", // Approximate year
  },
  {
    title: "Data Binding In Android",
    url: "https://medium.com/better-programming/how-to-bind-data-in-android-bbb1e180a928",
    category: "Android Development",
    tags: ["Android", "Data Binding", "UI"],
    date: "2020", // Approximate year
  },
  {
    title: "The Medium Dilemma",
    url: "https://medium.com/@tomerpacific/the-medium-dilemma-39307304558",
    category: "Writing",
    tags: ["Medium", "Publishing", "Content"],
    date: "2020", // Approximate year
  },
  {
    title: "How Expo Is Fooling Everyone",
    url: "https://medium.com/better-programming/how-expo-is-fooling-everyone-adf7f34d7528",
    category: "Mobile Development",
    tags: ["Expo", "React Native", "Opinion"],
    date: "2020", // Approximate year
  },
  {
    title: "Android Storage",
    url: "https://android.jlelse.eu/android-memory-cd45b82c0995",
    category: "Android Development",
    tags: ["Android", "Storage", "Memory Management"],
    date: "2020", // Approximate year
  },
  {
    title: "How To Approach A Coding Task",
    url: "https://medium.com/better-programming/how-to-approach-a-coding-task-15178b7f04fa",
    category: "Software Development",
    tags: ["Problem Solving", "Coding", "Best Practices"],
    date: "2020", // Approximate year
  },
  {
    title: "How Being A Mentor Can Be A Double Edged Sword",
    url: "https://www.freecodecamp.org/news/how-being-a-mentor-can-be-a-double-edged-sword/",
    category: "Career Development",
    tags: ["Mentoring", "Career", "Leadership"],
    date: "2019", // Approximate year
  },
  {
    title: "Broadcast Receivers",
    url: "https://android.jlelse.eu/broadcast-receivers-for-beginners-a9d7aa03fb76",
    category: "Android Development",
    tags: ["Android", "Broadcast Receivers", "System Events"],
    date: "2019", // Approximate year
  },
  {
    title: "Serialization In Flutter",
    url: "https://medium.com/flutter-community/serializing-your-object-in-flutter-ab510f0b8b47",
    category: "Flutter Development",
    tags: ["Flutter", "Serialization", "Data"],
    date: "2019", // Approximate year
  },
  {
    title: "PWA",
    url: "https://medium.freecodecamp.org/an-explanation-of-progressive-web-apps-for-the-non-pwa-crowd-8a400e275ea1",
    category: "Web Development",
    tags: ["PWA", "Web Development", "Progressive Web Apps"],
    date: "2019", // Approximate year
  },
  {
    title: "Managing Flutter Packages",
    url: "https://medium.com/flutter-community/managing-packages-in-flutter-6018cecaf3a7",
    category: "Flutter Development",
    tags: ["Flutter", "Packages", "Dependencies"],
    date: "2019", // Approximate year
  },
  {
    title: "A Different Approach To Hackathons",
    url: "https://medium.freecodecamp.org/a-different-approach-to-hackathons-b88960d9cb79",
    category: "Events",
    tags: ["Hackathons", "Events", "Development"],
    date: "2019", // Approximate year
  },
  {
    title: "Get A User's Location In Flutter",
    url: "https://medium.com/flutter-community/get-a-users-location-in-flutter-20f488ac8043",
    category: "Flutter Development",
    tags: ["Flutter", "Location", "Permissions"],
    date: "2019", // Approximate year
  },
  {
    title: "What I Should Have Known Before Publishing An Application",
    url: "https://hackernoon.com/what-i-should-have-known-before-publishing-an-application-898d8f72a0e2",
    category: "App Development",
    tags: ["Publishing", "Lessons Learned", "Mobile Apps"],
    date: "2019", // Approximate year
  },
  {
    title: "Android Menus",
    url: "https://medium.freecodecamp.org/an-introduction-to-android-menus-c9f382264b49",
    category: "Android Development",
    tags: ["Android", "UI", "Menus"],
    date: "2019", // Approximate year
  },
  {
    title: "Perseverance In Automation",
    url: "https://medium.swlh.org/perseverance-in-automation-147630672ba8",
    category: "Automation",
    tags: ["Automation", "Testing", "Development"],
    date: "2019", // Approximate year
  },
  {
    title: "How To Properly Give A Code Review",
    url: "https://medium.freecodecamp.org/how-to-properly-give-a-code-review-c2fcc49e345f",
    category: "Software Development",
    tags: ["Code Review", "Best Practices", "Team Work"],
    date: "2019", // Approximate year
  },
  {
    title: "What I've Learned From Publishing Stories On Medium",
    url: "https://medium.freecodecamp.org/what-ive-learned-from-publishing-stories-on-medium-9057da232465",
    category: "Writing",
    tags: ["Medium", "Writing", "Content Creation"],
    date: "2019", // Approximate year
  },
  {
    title: "Why Friendships Are Dead",
    url: "https://hackernoon.com/why-friendships-are-dead-4db6f27962da",
    category: "Personal",
    tags: ["Social", "Relationships", "Technology"],
    date: "2019", // Approximate year
  },
  {
    title: "Integrating Firebase",
    url: "https://medium.freecodecamp.org/how-to-integrate-firebase-with-your-application-74fdde01dfe2",
    category: "Backend Development",
    tags: ["Firebase", "Integration", "Backend"],
    date: "2019", // Approximate year
  },
  {
    title: "Flutter > React Native",
    url: "https://hackernoon.com/flutter-react-native-b5e82a2c3e82",
    category: "Mobile Development",
    tags: ["Flutter", "React Native", "Comparison"],
    date: "2019", // Approximate year based on Flutter's rise in popularity
  },
  {
    title: "How To Publish An Application In The Play Store",
    url: "https://medium.freecodecamp.org/how-to-publish-an-application-in-the-play-store-8ddcc6dc3587",
    category: "Android Development",
    tags: ["Android", "Google Play", "App Publishing"],
    date: "2018", // Approximate year
  },
  {
    title: "Chrome APIs",
    url: "https://medium.freecodecamp.org/features-of-the-chrome-api-you-should-know-bf5c8b6c7733",
    category: "Browser Extensions",
    tags: ["Chrome", "APIs", "Extensions"],
    date: "2018", // Approximate year
  },
  {
    title: "Android Visibility Listeners",
    url: "https://medium.freecodecamp.org/how-and-why-to-use-android-visibility-listeners-971e3b6c7733",
    category: "Android Development",
    tags: ["Android", "UI", "Event Listeners"],
    date: "2018", // Approximate year
  },
  {
    title: "Israel's IP Problem",
    url: "https://hackernoon.com/israels-ip-problem-7d8916cb93ec",
    category: "Tech Industry",
    tags: ["Israel", "Intellectual Property", "Tech Industry"],
    date: "2018", // Approximate year
  },
  {
    title: "Making Sense Of Android Layouts",
    url: "https://medium.freecodecamp.org/how-to-make-sense-of-the-many-android-layouts-693b262706e0",
    category: "Android Development",
    tags: ["Android", "UI Design", "Layouts"],
    date: "2018", // Approximate year
  },
  {
    title: "How To Publish A Chrome Extension",
    url: "https://medium.freecodecamp.org/how-to-publish-your-chrome-extension-dd8400a3d53",
    category: "Browser Extensions",
    tags: ["Chrome", "Web Store", "Publishing"],
    date: "2018", // Approximate year
  },
  {
    title: "How To Implement A Chrome Extension",
    url: "https://medium.freecodecamp.org/how-to-implement-a-chrome-extension-3802d63b5376",
    category: "Browser Extensions",
    tags: ["Chrome", "Extensions", "JavaScript"],
    date: "2018", // Approximate year
  },
  {
    title: "How To Contribute To An Open Source Project",
    url: "https://medium.com/@tomerpacific/how-to-contribute-to-an-open-source-repository-d66b5e99eec5",
    category: "Open Source",
    tags: ["Open Source", "GitHub", "Collaboration"],
    date: "2018", // Approximate year
  },
  {
    title: "Why Documentation Matters",
    url: "https://medium.com/@tomerpacific/documentation-matters-41ef62dd5c2f",
    category: "Software Development",
    tags: ["Documentation", "Best Practices", "Software Development"],
    date: "2018", // Approximate year
  },
  {
    title: "JS - Native Communication Bridges",
    url: "https://medium.com/@tomerpacific/burning-bridges-native-part-1-7baef82b3f02",
    category: "Mobile Development",
    tags: ["JavaScript", "Mobile Development", "Native Bridge"],
    date: "2018", // Approximate year
  },
  {
    title: "What A Developer Goes Through When Looking For A Second Job",
    url: "https://medium.freecodecamp.org/what-a-developer-goes-through-when-looking-for-a-second-job-f061c26ffd8f",
    category: "Career Development",
    tags: ["Career", "Job Search", "Software Development"],
    date: "2018", // Approximate year
  },
]

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-b from-slate-50 to-white">
      <div className="py-4">{/* Header removed */}</div>
      <main className="container py-8">
        <div className="grid gap-8 md:grid-cols-[1fr_300px]">
          <div className="space-y-8">
            <section className="space-y-6">
              <div className="flex flex-col items-start gap-4 md:flex-row md:items-center md:gap-8 bg-white p-6 rounded-xl shadow-sm border border-slate-100">
                <Avatar className="h-24 w-24 border-2 border-emerald-500 shadow-md">
                  <AvatarImage src="/images/profile.png" alt="Tomer Ben-Rachel" />
                  <AvatarFallback className="bg-emerald-100 text-emerald-700">TB</AvatarFallback>
                </Avatar>
                <div className="space-y-2">
                  <h1 className="text-3xl font-bold text-slate-800">Tomer Ben-Rachel</h1>
                  <p className="text-slate-600">
                    Software developer, technical writer, and open source contributor. Author of 72+ technical articles.
                  </p>
                </div>
              </div>
            </section>

            <div className="space-y-6">
              {articles.map((article, index) => (
                <Card
                  key={index}
                  className="overflow-hidden border-slate-200 hover:shadow-md transition-shadow duration-300"
                >
                  <CardHeader className="pb-2 bg-gradient-to-r from-slate-50 to-white">
                    <div className="flex justify-between items-start">
                      <div>
                        <CardTitle className="text-xl hover:text-emerald-600 transition-colors">
                          <Link href={article.url} target="_blank" rel="noopener noreferrer">
                            {article.title}
                          </Link>
                        </CardTitle>
                        <CardDescription className="text-emerald-700">{article.category}</CardDescription>
                      </div>
                      <div className="flex items-center text-sm text-slate-500 bg-slate-100 px-2 py-1 rounded-full">
                        <CalendarIcon className="mr-1 h-3 w-3" />
                        <span>{article.date}</span>
                      </div>
                    </div>
                  </CardHeader>
                  <CardFooter className="text-sm text-slate-500 pt-4 border-t border-slate-100">
                    <div className="flex items-center gap-2 flex-wrap">
                      {article.tags.map((tag, tagIndex) => (
                        <span key={tagIndex} className="bg-slate-100 text-slate-700 px-2 py-1 rounded-full text-xs">
                          {tag}
                        </span>
                      ))}
                    </div>
                  </CardFooter>
                </Card>
              ))}
            </div>
          </div>
          <aside className="space-y-6">
            <Card className="border-slate-200 shadow-sm bg-white">
              <CardHeader className="bg-emerald-50 border-b border-emerald-100">
                <CardTitle className="text-emerald-800">Topics</CardTitle>
              </CardHeader>
              <CardContent className="pt-4">
                <div className="flex flex-wrap gap-2">
                  <Button
                    variant="outline"
                    size="sm"
                    className="bg-slate-50 border-slate-200 hover:bg-emerald-50 hover:text-emerald-700 hover:border-emerald-200"
                  >
                    Android
                  </Button>
                  <Button
                    variant="outline"
                    size="sm"
                    className="bg-slate-50 border-slate-200 hover:bg-emerald-50 hover:text-emerald-700 hover:border-emerald-200"
                  >
                    Flutter
                  </Button>
                  <Button
                    variant="outline"
                    size="sm"
                    className="bg-slate-50 border-slate-200 hover:bg-emerald-50 hover:text-emerald-700 hover:border-emerald-200"
                  >
                    Jetpack Compose
                  </Button>
                  <Button
                    variant="outline"
                    size="sm"
                    className="bg-slate-50 border-slate-200 hover:bg-emerald-50 hover:text-emerald-700 hover:border-emerald-200"
                  >
                    Chrome Extensions
                  </Button>
                  <Button
                    variant="outline"
                    size="sm"
                    className="bg-slate-50 border-slate-200 hover:bg-emerald-50 hover:text-emerald-700 hover:border-emerald-200"
                  >
                    JavaScript
                  </Button>
                  <Button
                    variant="outline"
                    size="sm"
                    className="bg-slate-50 border-slate-200 hover:bg-emerald-50 hover:text-emerald-700 hover:border-emerald-200"
                  >
                    Firebase
                  </Button>
                  <Button
                    variant="outline"
                    size="sm"
                    className="bg-slate-50 border-slate-200 hover:bg-emerald-50 hover:text-emerald-700 hover:border-emerald-200"
                  >
                    Career
                  </Button>
                  <Button
                    variant="outline"
                    size="sm"
                    className="bg-slate-50 border-slate-200 hover:bg-emerald-50 hover:text-emerald-700 hover:border-emerald-200"
                  >
                    Open Source
                  </Button>
                </div>
              </CardContent>
            </Card>
            <Card className="border-slate-200 shadow-sm bg-white">
              <CardHeader className="bg-emerald-50 border-b border-emerald-100">
                <CardTitle className="text-emerald-800">Find Me On</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4 pt-4">
                <div className="space-y-2 p-3 rounded-lg hover:bg-slate-50 transition-colors">
                  <h3 className="font-medium text-slate-800 hover:text-emerald-600">
                    <Link href="https://github.com/TomerPacific" target="_blank" rel="noopener noreferrer">
                      GitHub
                    </Link>
                  </h3>
                  <p className="text-xs text-slate-600">Check out my open source projects and contributions</p>
                </div>
                <div className="space-y-2 p-3 rounded-lg hover:bg-slate-50 transition-colors">
                  <h3 className="font-medium text-slate-800 hover:text-emerald-600">
                    <Link href="https://medium.com/@tomerpacific" target="_blank" rel="noopener noreferrer">
                      Medium
                    </Link>
                  </h3>
                  <p className="text-xs text-slate-600">Read all my technical articles and tutorials</p>
                </div>
                <div className="space-y-2 p-3 rounded-lg hover:bg-slate-50 transition-colors">
                  <h3 className="font-medium text-slate-800 hover:text-emerald-600">
                    <Link
                      href="https://play.google.com/store/apps/developer?id=tomerpacific"
                      target="_blank"
                      rel="noopener noreferrer"
                    >
                      Google Play
                    </Link>
                  </h3>
                  <p className="text-xs text-slate-600">Check out my Android applications</p>
                </div>
              </CardContent>
            </Card>
          </aside>
        </div>
      </main>
    </div>
  )
}
