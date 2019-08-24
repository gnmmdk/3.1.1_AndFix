#include <jni.h>
#include <string>
#include <android/log.h>
#include "art_5_1.h"

extern "C"
JNIEXPORT void JNICALL
Java_com_kangjj_andfix_test_DexManager_replace(JNIEnv *env, jobject instance, jobject bugMethod,
                                               jobject fixMethod) {
    //获得指向被替换方法的指针
    art::mirror::ArtMethod *bugArtMethod = reinterpret_cast<art::mirror::ArtMethod *>(env->FromReflectedMethod(bugMethod));
    //获得指向修改完bug后的方法指针
    art::mirror::ArtMethod *fixedArtMethod = reinterpret_cast<art::mirror::ArtMethod *>(env->FromReflectedMethod(fixMethod));

    reinterpret_cast<art::mirror::Class *>(fixedArtMethod->declaring_class_)->class_loader_=
            reinterpret_cast<art::mirror::Class *>(bugArtMethod->declaring_class_)->class_loader_;
    reinterpret_cast<art::mirror::Class *>(fixedArtMethod->declaring_class_)->clinit_thread_id_ =
            reinterpret_cast<art::mirror::Class *>(bugArtMethod->declaring_class_)->clinit_thread_id_;
    reinterpret_cast<art::mirror::Class *>(fixedArtMethod->declaring_class_)->status_ =
            reinterpret_cast<art::mirror::Class *>(bugArtMethod->declaring_class_)->status_-1;
    reinterpret_cast<art::mirror::Class *>(fixedArtMethod->declaring_class_)->super_class_ = 0 ;

    //把函数的成员变量替换成新函数
    bugArtMethod->declaring_class_=fixedArtMethod->declaring_class_;
    bugArtMethod->dex_cache_resolved_methods_ = fixedArtMethod->dex_cache_resolved_methods_;
    bugArtMethod->dex_cache_resolved_types_ = fixedArtMethod->dex_cache_resolved_types_;
    bugArtMethod->access_flags_ = fixedArtMethod->access_flags_;
    bugArtMethod->dex_code_item_offset_ = fixedArtMethod->dex_code_item_offset_;
    bugArtMethod->method_index_ = fixedArtMethod->method_index_;
    bugArtMethod->dex_method_index_ = fixedArtMethod->dex_method_index_;

    bugArtMethod->ptr_sized_fields_.entry_point_from_interpreter_ = fixedArtMethod->ptr_sized_fields_.entry_point_from_interpreter_;
    bugArtMethod->ptr_sized_fields_.entry_point_from_jni_ = fixedArtMethod->ptr_sized_fields_.entry_point_from_jni_;
    bugArtMethod->ptr_sized_fields_.entry_point_from_quick_compiled_code_ = fixedArtMethod->ptr_sized_fields_.entry_point_from_quick_compiled_code_;

    __android_log_print(ANDROID_LOG_ERROR,"AndFix","Replace_5_1:%d,%d",
                        static_cast<const char*>(bugArtMethod->ptr_sized_fields_.entry_point_from_quick_compiled_code_),
                        fixedArtMethod->ptr_sized_fields_.entry_point_from_quick_compiled_code_);
}