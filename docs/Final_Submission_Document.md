# Pass Task 2.1 Submission

Name: `Your Name`  
Student ID: `Your Student ID`  
Unit: `SIT305`  

## 1. LLM Declaration Statement

I used a Large Language Model to assist with selected parts of this task. AI support was used for the initial structure of the Android app, parts of the conversion logic, validation and error-handling ideas, debugging some Gradle and Android setup issues, and drafting the first version of the Gemini Nano research report. I also used follow-up LLM help to fix issues in `MainActivity.kt` related to dropdown state, input validation, and invalid conversion handling after reviewing the app behaviour. I reviewed the final code, tested the app, simplified the project structure, and edited the written content before submission.

The parts that were most influenced by AI were:

- the dropdown state logic and selected-unit handling in `MainActivity.kt`
- the validation logic for empty input, non-numeric input, and negative fuel values
- the conversion logic for currency, fuel, and temperature
- fixing stale dropdown selections and restoring safe conversion behaviour in `MainActivity.kt`
- troubleshooting build issues related to Java, Gradle, and Android SDK setup
- the first draft of the Gemini Nano research report

Prompt used: `Read the assignment task, help build the Travel Companion Android app, assist with debugging, and help draft the Gemini Nano research section.`  

Reason for using this prompt: I used this prompt because it matched the exact requirements of the task and helped me get support with both the coding part and the written research component. The final submission was reviewed and adjusted by me before completion.

## 2. Subtask 3: Gemini Nano Research Report

Gemini Nano is Google’s lightweight generative AI model designed to run directly on supported Android devices. Unlike cloud-based AI systems that send data to external servers for processing, Gemini Nano is built for on-device tasks, which makes it especially relevant in mobile app development. Google’s Android documentation explains that Gemini Nano can be accessed through AICore, a system-level service that manages the model on the device and allows apps to use on-device generative AI without shipping a large model inside the app itself. This is important because it reduces setup complexity for developers while also improving privacy, response speed, and offline support.

One of the biggest advantages of Gemini Nano is privacy. When prompts and outputs are handled locally, sensitive user data does not always need to leave the phone. This is useful in mobile apps because phones often store personal messages, travel information, notes, calendar data, and accessibility-related content. Local processing reduces the risk of exposing that information through network requests. It also improves reliability when users are travelling, have poor signal, or want AI features without depending on a constant internet connection.

Recent research on mobile LLMs also supports this direction. Studies on LLMs in mobile apps point out that cloud AI can introduce latency, subscription costs, and privacy concerns, while on-device AI is increasingly attractive as mobile hardware improves. At the same time, researchers note that mobile devices still have resource limits, so smaller and more efficient models are better suited to focused tasks such as rewriting, summarising, classifying, and smart replies. This is where Gemini Nano fits well in Android development.

There are several realistic ways on-device AI can improve privacy or offline functionality in mobile apps. First, a travel app could summarise itinerary details, hotel confirmations, or long travel emails directly on the device, which would protect private travel information. Second, a messaging app could generate smart replies locally so that personal conversations are not sent to a server just to suggest a short response. Third, a notes app could offer offline rewriting or proofreading while the user is on a plane or in an area with poor reception. Fourth, an accessibility app could generate image descriptions or screen summaries on the device, which would help users while also keeping private content local. Fifth, a productivity app could classify text, create short summaries, or reorganise meeting notes even when the phone is offline.

Google’s own examples also show why this model is practical on phones. Android documentation lists use cases such as smart reply, proofreading, text rephrasing, and summarisation, which are all tasks that fit naturally into everyday mobile apps without needing a constant network connection.

Overall, Gemini Nano is a strong example of how AI is shifting from cloud-only systems to practical mobile use. For Android developers, it offers a way to build features that are faster, more private, and more reliable in offline situations. Although it is not meant to replace all cloud AI use cases, it is well suited to focused mobile tasks where privacy, responsiveness, and on-device convenience matter most.

## 3. YouTube Demonstration Video Link

YouTube link: `Add your clickable YouTube link here`

## 4. GitHub Repository Link

GitHub repository: [https://github.com/sennan21/SIT305-Mobile-Application-Development.git](https://github.com/sennan21/SIT305-Mobile-Application-Development.git)

## 5. LLM Conversation Link

LLM conversation link: [https://chatgpt.com/share/69bf789b-8238-800e-a665-41f66ffa16d0](https://chatgpt.com/share/69bf789b-8238-800e-a665-41f66ffa16d0)

If no public shareable conversation link is available for the platform used, state that clearly in the final exported PDF and keep the prompt and explanation shown in the declaration section above.

## References Used For The Research Report

- Android Developers, Google AI Edge SDK / Gemini Nano: [https://developer.android.com/ai/gemini-nano/ai-edge-sdk](https://developer.android.com/ai/gemini-nano/ai-edge-sdk)
- [ResearchPap1.pdf](/Users/sennanhettirachchi/Downloads/305/researchpapers/ResearchPap1.pdf)
- [ResearchPap2.pdf](/Users/sennanhettirachchi/Downloads/305/researchpapers/ResearchPap2.pdf)
- [ResearchPaper3.pdf](/Users/sennanhettirachchi/Downloads/305/researchpapers/ResearchPaper3.pdf)
