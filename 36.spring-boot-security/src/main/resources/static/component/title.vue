<template>
  <h3>{{ title }}</h3>
</template>
<script>
module.exports = {
  name: 'App',
  props: {
    title: {
      type: String,
      default: '首页'
    }
  },
  created() {
    console.log('created')
  },
  data() {
    return {}
  },
  mounted() {
    console.log('mounted')
  },
  methods: {}
}
</script>
