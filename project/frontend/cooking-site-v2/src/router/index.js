import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/recipeView',
    name: 'RecipeView',
    component: () => import('../views/recipeView/RecipeView.vue')
  },
  {
    path: '/ratingView',
    name: 'RatingView',
    component: () => import('../views/recipeView/RatingView.vue')
  },
  {
    path: '/newRecipe',
    name: 'NewRecipe',
    component: () => import('../views/recipeView/NewRecipe.vue')
  },
  {
    path: '/account',
    name: 'Account',
    component: () => import('../views/Account.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },

]

const router = new VueRouter({
  routes
})

export default router
