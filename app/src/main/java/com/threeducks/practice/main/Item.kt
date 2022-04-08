package com.threeducks.practice.main

class Item(var imageURL: String, var tags: String) {
    fun imageURL(imageURL: String) {
        this.imageURL = imageURL
    }

    fun tags(tags: String) {
        this.tags = tags
    }
}