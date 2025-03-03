<template>
  <div class="app">
    <header>
      <img
        alt="GitHub logo"
        src="https://avatars.githubusercontent.com/u/194215478?s=200&v=4"
        width="125"
        height="125"
      />
    </header>
    <main>
      <HelloWorld />
    </main>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'App',
  components: {
    HelloWorld
  }
}
</script>

<style>
.app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
