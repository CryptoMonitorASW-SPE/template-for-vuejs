<template>
  <div class="hello-world">
    <h1>{{ msg }}</h1>
    <p>Welcome to your Vue.js application running with Vite</p>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data() {
    return {
      msg: 'Hello World!'
    }
  }
}
</script>

<style scoped>
.hello-world h1 {
  font-size: 2.5rem;
  color: #42b983;
}

.hello-world {
  margin: 0 auto;
  max-width: 800px;
}
</style>
