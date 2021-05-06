const app=  Vue.createApp({

    data() {
        return {  
            users : [
                {
                    username: 'burcu',
                    password: 123
                },
                {
                    username: 'aleks',
                    password: 123
                }
            ]
        }
    }, 
    methods: {
      changePassword() {
          this.password++
      }  
    }
}


)

app.mount('#ourapp')