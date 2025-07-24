package com.example.notemark.note.presentation.preview

import com.example.notemark.note.domain.Note
import java.time.Instant

data object PreviewModels {
    val note = Note(
        id = "0",
        title = "Note Title",
        content = "The quiet hum of the morning air was broken only by the soft rustle of leaves dancing in the breeze. A cup of coffee steamed gently in hand, warmth seeping into chilled fingers. Thoughts flowed freely, unbothered by structure or form—just fragments of memory and hope stitched together. In this stillness, inspiration felt close, like a familiar friend waiting to be acknowledged and welcomed in. Pages remained blank, yet full of potential. Each pause between thoughts was its own kind of music. The world outside hadn’t changed, but the lens through which it was viewed felt freshly cleaned—clearer, softer. Clouds moved lazily across the sky, painting slow-moving stories above. A breeze swept in, carrying with it the faint scent of earth and something that reminded one of home. The clinking of a spoon against a ceramic mug, the distant bark of a dog, a laugh from a neighbor’s open window—each moment ordinary, yet profound. And somehow, all of it seemed to whisper: write, because this matters.",
        createdAt = Instant.now(),
        lastEditedAt = Instant.now()
    )
}