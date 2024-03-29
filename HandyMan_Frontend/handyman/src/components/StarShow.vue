<template>
    <div class="rating">
      <span v-for="star in stars" :key="star" @click="setRating(star)" :class="{ 'filled': star <= rating }">&#9733;</span>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      value: {
        type: Number,
        default: 0
      }
    },
    data() {
      return {
        rating: this.value,
        stars: [1, 2, 3, 4, 5]
      };
    },
    methods: {
      setRating(rating) {
        this.rating = rating;
        this.$emit('input', rating);
      }
    }
  };
  </script>
  
  <style scoped>
  .rating {
    font-size: 24px;
  }
  
  .filled {
    color: gold;
  }
  </style>
  