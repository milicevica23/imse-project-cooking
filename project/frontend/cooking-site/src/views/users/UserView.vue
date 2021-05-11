<template>
    <div v-if="users.length">
        <div>
            HELLO I AM USER
        </div>
        <div v-for="user in users" :key="user"> 
        <p>{{user.name}}</p>   
        <p>{{user.email}}</p>   
        <p>{{user.username}}</p>   
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return {
            users: []
        }
    },
    methods: {
    },
    mounted(){
        fetch("http://localhost:8080/user/listUsers")
            .then(res => res.json())
            .then(data => this.users = data)
            .catch(e => console.log(e))
    }
}
</script>