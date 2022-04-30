const router = require('express').Router();


router.get('/', (req, res) => {

        return res.render('homy', {
            title: 'Home',
            css: 'style'
            // user: req.user,
            // message: req.flash('message')
        })

});
module.exports = router;
